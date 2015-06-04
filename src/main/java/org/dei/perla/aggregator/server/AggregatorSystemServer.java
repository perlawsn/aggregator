package org.dei.perla.aggregator.server;

import org.apache.log4j.Logger;
import org.dei.perla.aggregator.proxyfpc.ProxyFpc;
import org.dei.perla.aggregator.system.Aggregator;
import org.dei.perla.core.descriptor.DeviceDescriptorParser;
import org.dei.perla.core.descriptor.JaxbDeviceDescriptorParser;
import org.dei.perla.core.fpc.FpcFactory;
import org.dei.perla.core.registry.Registry;
import org.dei.perla.core.registry.TreeRegistry;



public class AggregatorSystemServer {
	 private static final Logger log = Logger.getLogger(AggregatorSystemServer.class);
	 private TreeRegistry registry;
	 private Aggregator aggregator=new Aggregator(registry);
	 
	 public AggregatorSystemServer(){
		 registry=new TreeRegistry();
	 }

	 
	 
	 
	    

}
