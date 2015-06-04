package org.dei.perla.aggregator.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dei.perla.core.DeviceConnectionException;
import org.dei.perla.core.Plugin;
import org.dei.perla.core.channel.Channel;
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
import org.dei.perla.core.registry.Registry;
import org.dei.perla.core.registry.TreeRegistry;
import org.dei.perla.core.sample.Attribute;

public class AggregatorSystemClient {

    private static final Logger log = Logger.getLogger(AggregatorSystemClient.class);

    private final DeviceDescriptorParser parser;
    private final FpcFactory factory;
    private final TreeRegistry registry;
    
    private final FactoryHandler fctHand = new FactoryHandler();

    /**
     * Creates a new {@code PerLaSystem} object configured with the required
     * {@link Plugin}s.
     *
     * @param plugins plugin objects to use in the PerLa installation
     *                (MapperFactory and ChannelPlugin)
     */
    public AggregatorSystemClient(List<Plugin> plugins) {
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
        
        //After creation of a complete Perla System, it is connected to a
        //Server Perla System, with Socket Technology
        
        PerlaClientSocket client = new PerlaClientSocket("127.0.0.1",1377);

        try {
       	 client.startClient();
        }
        catch(IOException e ){
       	 System.err.println(e.getMessage());

       }
        
                
        
    }

    /**
     * Off-band Device Descriptor injection. This method allows the creation
     * of {@link Fpc} proxies for devices whose {@link Channel} cannot relay
     * Device Descriptor information.
     *
     * @param is Device Descriptor {@link InputStream}
     * @return the newly created {@link Fpc} object
     * @throws DeviceDescriptorException if the {@link Fpc} creation process
     * fails due to an error in the Device Descriptor
     */
    public Fpc injectDescriptor(InputStream is)
            throws DeviceConnectionException {
        try {
            DeviceDescriptor d = parser.parse(is);
            Fpc fpc = factory.createFpc(d, registry);
            registry.add(fpc);
            return fpc;
        } catch (Exception e) {
            throw new DeviceConnectionException("Error injecting Device " +
                    "Descriptor", e);
        }
    }

    /**
     * Returns the FPC {@link Registry} in use inside this object.
     *
     * @return FPC {@link Registry} instance
     */
    public Registry getRegistry() {
        return registry;
    }

    /**
     * IOHandler for processing FPC Device Descriptors
     *
     *
     */
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
                
                //Qui mando sul socket l'avviso che è stato creato un FPC
                FpcSender fpcSender=new FpcSender(fpc.getAttributes());
                fpcSender.getFpcString();
                
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
