package org.dei.perla.aggregator.proxyfpc;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.dei.perla.core.fpc.Fpc;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.core.sample.Attribute;

public class ProxyFpc implements Fpc {

	
	private final int id;
	private final int aggregatorId;
    private final String type = "proxyFpc";
	private final Set<Attribute> atts;
    private final Map<Attribute, Object> attValues;
	
	
	
	protected ProxyFpc(int id, int aggregatorId, Set<Attribute> atts,
            Map<Attribute, Object> attValues) {
		this.id = id;
		this.aggregatorId=aggregatorId;
        this.atts = atts;
        this.attValues = attValues;
                		
	}
	
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public Collection<Attribute> getAttributes() {
		// TODO Auto-generated method stub
		return atts;
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


	public int getAggregatorId() {
		return aggregatorId;
	}



}
