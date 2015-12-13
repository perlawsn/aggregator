package org.dei.perla.aggregator.pms.node;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dei.perla.core.Plugin;
import org.dei.perla.core.channel.http.HttpChannelPlugin;
import org.dei.perla.core.channel.simulator.SimulatorChannelPlugin;
import org.dei.perla.core.channel.simulator.SimulatorMapperFactory;
import org.dei.perla.core.fpc.FpcCreationException;
import org.dei.perla.core.message.json.JsonMapperFactory;
import org.dei.perla.core.message.urlencoded.UrlEncodedMapperFactory;


public class ClientMain {

	 private static final String descPath ="src/main/java/org/dei/perla/aggregator/pms/node/simulator.xml";
	
	 
	 
	 private static final List<Plugin> plugins;
	    static {
	        List<Plugin> ps = new ArrayList<>();
	        ps.add(new JsonMapperFactory());
	        ps.add(new UrlEncodedMapperFactory());
	        ps.add(new SimulatorMapperFactory());
	        ps.add(new HttpChannelPlugin());
	        ps.add(new SimulatorChannelPlugin());
	        plugins = Collections.unmodifiableList(ps);
	    }	
	    
	    
	  public static void main(String args[]){
	    	AggregatorSystem as = new AggregatorSystem(plugins);
	    	
	    	try {
				as.injectDescriptor(new FileInputStream(descPath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FpcCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
}


