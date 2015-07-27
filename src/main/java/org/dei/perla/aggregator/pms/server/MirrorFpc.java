package org.dei.perla.aggregator.pms.server;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.dei.perla.core.fpc.Fpc;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.core.fpc.base.ChannelManager;
import org.dei.perla.core.fpc.base.Scheduler;
import org.dei.perla.core.sample.Attribute;

public class MirrorFpc implements Fpc {
		
		private final String nodeId;
	  	private final int id;
	    private final String type;
	    private final Set<Attribute> atts;
	

	    protected MirrorFpc(int id, String type, Set<Attribute> atts, String nodeId) {
	        this.id = id;
	        this.type = type;
	        this.atts = atts;
	        this.nodeId = nodeId;
	    }

	    @Override
	    public int getId() {
	        return id;
	    }

	    @Override
	    public String getType() {
	        return type;
	    }

	    @Override
	    public Set<Attribute> getAttributes() {
	        return atts;
	    }

		@Override
		public Task set(Map<Attribute, Object> values, boolean strict,
				TaskHandler handler) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Task get(List<Attribute> atts, boolean strict,
				TaskHandler handler) {
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
		public Task async(List<Attribute> atts, boolean strict,
				TaskHandler handler) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void stop(Consumer<Fpc> handler) {
			// TODO Auto-generated method stub
			
		}
}
