package org.dei.perla.aggregator.pms.server;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.dei.perla.aggregator.pms.types.GetMessage;
import org.dei.perla.core.fpc.Fpc;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.core.sample.Attribute;

public class MirrorFpc implements Fpc {
		
		private final String nodeId;
	  	private final int id;
	    private final String type;
	    private final Set<Attribute> atts;
	    private ServerMethods servMsgProd = new ServerMethods();

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
		
		/*In ogni metodo get va definito l'handler che riceverà i messaggi
		 *lo "smistatore" dei messaggi che arrivano avrà quindi un registro
		 *con dentro gli handler 
		 */

		@Override
		public Task get(List<Attribute> atts, boolean strict,
				TaskHandler handler) {
						
			MirrorTask mirTask = new MirrorTask(atts, handler, strict, nodeId);
			
			mirTask.start();
						
			return mirTask;
		}

		@Override
		public Task get(List<Attribute> atts, boolean strict, long periodMs,
				TaskHandler handler) {
			
			MirrorTask mirTask = new MirrorTask(atts, handler, strict, periodMs, nodeId);
			
			mirTask.start();
			
			return mirTask;
		}

		@Override
		public Task async(List<Attribute> atts, boolean strict,
				TaskHandler handler) {
			
			MirrorTask mirTask = new MirrorTask(atts, handler, strict, true, nodeId);
			
			mirTask.start();
			
			return mirTask;
								
			
		}

		@Override
		public void stop(Consumer<Fpc> handler) {
			// TODO Auto-generated method stub
			
		}
}
