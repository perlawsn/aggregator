package org.dei.perla.aggregator.pms.server;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.dei.perla.core.fpc.Fpc;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.core.sample.Attribute;

public class MirrorFpc implements Fpc {

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
	public Task set(Map<Attribute, Object> values, boolean strict,
			TaskHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task get(List<Attribute> atts, boolean strict, TaskHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task get(List<Attribute> atts, boolean strict, long periodMs,
			TaskHandler handler) {
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