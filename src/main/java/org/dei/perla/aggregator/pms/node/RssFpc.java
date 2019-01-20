package org.dei.perla.aggregator.pms.node;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.dei.perla.core.fpc.Attribute;
import org.dei.perla.core.fpc.Fpc;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.socket.RemoteFileReader;
import org.dei.perla.socket.RssReader;

public class RssFpc implements Fpc {
	
	private static String rssFeedUrl;
	private static Integer period;	
	private static RemoteFileReader reader;
	
	/**
	 * Questa è la classe feed
	 * @param rssFeedUrl
	 * @param period
	 */
	
	public RssFpc(String rssFeedUrl, Integer period){
		
		this.rssFeedUrl = rssFeedUrl;
		this.period = period;
		reader= new RemoteFileReader(rssFeedUrl, period);
		reader.start();
	}

	
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Attribute> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task set(Map<Attribute, Object> values, boolean strict, TaskHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task get(List<Attribute> atts, boolean strict, TaskHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task get(List<Attribute> atts, boolean strict, long periodMs, TaskHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task async(List<Attribute> atts, boolean strict, TaskHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stop(Consumer<Fpc> handler) {
		// TODO Auto-generated method stub
		
	}

}
