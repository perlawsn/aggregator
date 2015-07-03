package org.dei.perla.aggregator.pms.node.system;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dei.perla.aggregator.pms.node.NodeAdmin;
import org.dei.perla.aggregator.pms.types.AddFpcMessage;
import org.dei.perla.core.DeviceConnectionException;
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
import org.dei.perla.core.fpc.FpcFactory;
import org.dei.perla.core.fpc.base.BaseFpcFactory;
import org.dei.perla.core.message.MapperFactory;
import org.dei.perla.core.registry.TreeRegistry;

public class AggregatorSystem {
	private static final Logger log = Logger.getLogger(PerLaSystem.class);
	
	private NodeAdmin nodeAdmin = new NodeAdmin();
	private NodeMethods nodeMethods = new NodeMethods();
	private final String nodeId;
	
	private final FpcFactory factory;
	private final DeviceDescriptorParser parser;
	private final TreeRegistry registry;
	private final FactoryHandler fctHand = new FactoryHandler();
	
	public AggregatorSystem(List<Plugin> plugins) throws Exception{
		 	//Initialize the connection with a server and receives a node ID
			
			nodeId = nodeAdmin.createNodeContext();
			
			registry = new TreeRegistry();
		 
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
	
	
	   public Fpc injectDescriptor(InputStream is)
	            throws DeviceConnectionException {
	        try {
	            DeviceDescriptor d = parser.parse(is);
	            Fpc fpc = factory.createFpc(d, registry);
	            registry.add(fpc);
	            
	            //Notifica della creazione dell'Fpc al server superiore
	            HashMap<String, String> map = nodeMethods.generateListAttributes(fpc.getAttributes());
	            AddFpcMessage addFpcOnServer = new AddFpcMessage(nodeId, fpc.getId(), map );
	            nodeAdmin.sendFpcMessage(addFpcOnServer);
	                        
	            return fpc;
	        } catch (Exception e) {
	            throw new DeviceConnectionException("Error injecting Device " +
	                    "Descriptor", e);
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
	                DeviceDescriptor d = parser.parse(is);
	                Fpc fpc = factory.createFpc(d, registry);
	                registry.add(fpc);
	            } catch (Exception e) {
	                log.error("Cannot create Fpc", e);
	            }
	        }

	        @Override
	        public void error(IORequest request, Throwable cause) {
	            log.error("Unexpected error while waiting for Device Descriptor",
	                    cause);
	        }

	    }

	}
	
	

