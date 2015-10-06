package org.dei.perla.aggregator.pms.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dei.perla.core.Plugin;
import org.dei.perla.core.channel.http.HttpChannelPlugin;
import org.dei.perla.core.channel.simulator.SimulatorChannelPlugin;
import org.dei.perla.core.channel.simulator.SimulatorMapperFactory;
import org.dei.perla.core.message.json.JsonMapperFactory;
import org.dei.perla.core.message.urlencoded.UrlEncodedMapperFactory;
import org.dei.perla.core.registry.TreeRegistry;

public class ServerMain {

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
		ServerAdmin servAdmin;
		ServerConsumer servConsumer;
		
		PerLaSystem ps= new PerLaSystem(plugins);
		servAdmin=new ServerAdmin();
		
		servConsumer=new ServerConsumer((TreeRegistry)ps.getRegistry());
		Thread serverThread=new Thread(servConsumer);
		servAdmin.createNodeContext();
		serverThread.start();
		
		
	}
	
}
