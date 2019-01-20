package org.dei.perla.aggregator.pms.node;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dei.perla.core.PerLaSystem;
import org.dei.perla.core.Plugin;
import org.dei.perla.core.channel.ChannelFactory;
import org.dei.perla.core.channel.ChannelPlugin;
import org.dei.perla.core.channel.IOHandler;
import org.dei.perla.core.channel.IORequest;
import org.dei.perla.core.channel.IORequestBuilderFactory;
import org.dei.perla.core.channel.Payload;
import org.dei.perla.core.descriptor.DeviceDescriptor;
import org.dei.perla.core.descriptor.DeviceDescriptorParser;
import org.dei.perla.core.descriptor.JaxbDeviceDescriptorParser;
import org.dei.perla.core.fpc.Fpc;
import org.dei.perla.core.fpc.FpcCreationException;
import org.dei.perla.core.fpc.FpcFactory;
import org.dei.perla.core.fpc.base.BaseFpcFactory;
import org.dei.perla.core.message.MapperFactory;
import org.dei.perla.core.registry.Registry;
import org.dei.perla.core.registry.TreeRegistry;
import org.dei.perla.web.aggr.types.AddFpcMessage;

public class AggregatorSystem {
	private static final Logger log = Logger.getLogger(PerLaSystem.class);
	
	/**
	 * Documentazione gennaio 2019
	 * Questa classe è l'estensione della classe tipica del System di PerLa, 
	 * adattatata alla situazione di un aggregator che si connette al server centrale.
	 * VIene quindi inizializzato l'aggregator Admin e viene inizializzata la classe AggregatorMethods.
	 * I metodi da notare sono:
	 * 
	 * injectDescriptor:
	 * questo metodo, ricevuto un descrittore, crea un fpc
	 * 
	 * registry.add(fpc):
	 * questo metodo può essere usato in due modi. Il primo tramite il richiamo da injectDescriptor,
	 * il secondo invece tramite il richiamo diretto, dopo aver creato "a mano" una classe FPC completa.
	 * Inject descriptor infatti non fa altro che ricevere un descrittore e trasformarlo 
	 * in una classe FPC.
	 * 
	 * AddFpcMessage addFpcOnServer = new AddFpcMessage(nodeId, fpc.getId(), fpc.getAttributes() ):
	 * Il FPC appena creato viene mandato al server centrale. Viene creata una classe AddFpcMessage
	 * contenente Id e Attributi, che viene poi inviata tramite il metodo:
	 * 
	 * nodeMethods.sendFpcMessage(addFpcOnServer);
	 */
	
	private AggregatorAdmin nodeAdmin = new AggregatorAdmin(16010, "16400");
	private AggregatorMethods nodeMethods = new AggregatorMethods();
	private AggregatorConsumer aggrConsumer;
	private final String nodeId;
		
	private final FpcFactory factory;
	private final DeviceDescriptorParser parser;
	private final TreeRegistry registry;
	private final FactoryHandler fctHand = new FactoryHandler();
	private final Thread consumerThread;
	
	public AggregatorSystem(List<Plugin> plugins) {
		 	
		//Initialize the connection with a server and receives a node ID
			registry = new TreeRegistry();
			nodeId = nodeAdmin.createNodeContext();
			//Lancio un consumer che attende query dal server
            aggrConsumer = new AggregatorConsumer (nodeId, registry);
            
            consumerThread = new Thread(aggrConsumer);
						
				 
		    // Initialize default Device Descriptor packages
	        Set<String> pkgs = new HashSet<>();
	        pkgs.add("org.dei.perla.core.descriptor");
	        pkgs.add("org.dei.perla.core.descriptor.instructions");

	        // Parse user-defined Plugins
	        List<MapperFactory> maps = new ArrayList<>();
	        List<ChannelFactory> chans = new ArrayList<>();
	        List<IORequestBuilderFactory> reqs = new ArrayList<>();
	        for (Object p : plugins) {
	            pkgs.add(p.getClass().getPackage().getName());
	            if (p instanceof MapperFactory) {
	                // Manage mapper plugin
	                maps.add((MapperFactory) p);

	            } else if (p instanceof ChannelPlugin) {
	                // Manage channel plugin
	                ChannelPlugin cp = (ChannelPlugin) p;
	                cp.registerFactoryHandler(fctHand);
	                chans.add(cp.getChannelFactory());
	                reqs.add(cp.getIORequestBuilderFactory());

	            } else {
	                throw new IllegalArgumentException("Unknown plugin type " +
	                        p.getClass().getName());
	            }
	        }

	        // Create FPC Factory
	        parser = new JaxbDeviceDescriptorParser(pkgs);
	        factory = new BaseFpcFactory(maps, chans, reqs);

	}
	

	   public void start(){
		   consumerThread.start();
		   System.out.println("Consumer started");
	   }
	 
	   public Fpc injectDescriptor(InputStream is) throws FpcCreationException {
	        int id = -1;
	        boolean idGenerated = false;

	        try {

	            DeviceDescriptor d = parser.parse(is);

	            if (d.getId() != null) {
	                id = d.getId();
	            } else {
	                id = registry.generateID();
	                idGenerated = true;
	            }

	            Fpc fpc = factory.createFpc(d, id);
	            System.out.println("adding FPC"+id);
	            registry.add(fpc);
	                       
	            
	            //Notifica della creazione dell'Fpc al server superiore
	            
	            AddFpcMessage addFpcOnServer = new AddFpcMessage(nodeId, fpc.getId(), fpc.getAttributes() );
	            nodeMethods.sendFpcMessage(addFpcOnServer);
	            	            
	            return fpc;

	        } catch(Exception e) {
	            if (idGenerated) {
	                registry.releaseID(id);
	            }
	            String msg = "Error creating Fpc '" + id + "'";
	            log.error(msg, e);
	            throw new FpcCreationException(msg, e);
	        }
	    
	    
            
	   }

	 
	
	   private final class FactoryHandler implements IOHandler {

	        @Override
	        public void complete(IORequest request, Optional<Payload> result) {
	            result.map(Payload::asInputStream)
	                    .ifPresent(this::addFpc);
	        }

	        private void addFpc(InputStream is) {
	            try {
	                injectDescriptor(is);
	            } catch (FpcCreationException e) {
	                log.error(e);
	            }
	        }

	        @Override
	        public void error(IORequest request, Throwable cause) {
	            log.error("Unexpected error while waiting for Device Descriptor",
	                    cause);
	        }

	    }
	   
	   public Registry getRegistry(){
		   
		   return registry;
		   
	   }

	}
	
	

