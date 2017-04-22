package org.dei.perla.aggregator.pms.node;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.dei.perla.core.fpc.Attribute;
import org.dei.perla.core.fpc.Fpc;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;

public class RssFpc implements Fpc {
	
	private String rssFeed;
	
	public RssFpc(String rssFeed){
		
		this.rssFeed = rssFeed;
		
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
