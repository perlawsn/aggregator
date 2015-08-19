package org.dei.perla.aggregator.pms.server;

import java.util.List;
import java.util.Random;

import org.dei.perla.aggregator.pms.types.GetMessage;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.core.sample.Attribute;
import org.dei.perla.core.sample.Sample;
import org.dei.perla.core.sample.SamplePipeline;

public class MirrorTask implements Task{
	
	private final List<Attribute> atts;
	private final MirrorTaskHandler handler;
	private final int fpcId;
	private final String nodeId;
	private final String queue;
	private ServerMethods servMsgProd = new ServerMethods();
	private boolean hasStarted = false;
    private boolean running = false;
    private SamplePipeline pipeline;
    
    
	public MirrorTask(List <Attribute> atts, TaskHandler handler, boolean strict, 
			long periodMs, String nodeId, int fpcId){
		
		
		this.atts=atts;
		this.handler=(MirrorTaskHandler) handler;
		this.fpcId=fpcId;
		this.nodeId=nodeId;
		
		queue = generateQueue();
		
		subscribeQueue();
		GetMessage reqMess = new GetMessage(atts, strict, false, periodMs, nodeId, this.fpcId);
		try {
			servMsgProd.sendGetMessage(reqMess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startConsumer();
		
	}
	
	public MirrorTask(List <Attribute> atts, TaskHandler handler, boolean strict, 
			String nodeId, int fpcId){
		
		this.atts=atts;
		this.handler=(MirrorTaskHandler) handler;
		this.fpcId=fpcId;
		this.nodeId=nodeId;
		
		queue = generateQueue();
		
		subscribeQueue();
		GetMessage reqMess = new GetMessage(atts, strict, false, -1, nodeId, this.fpcId);
		try {
			servMsgProd.sendGetMessage(reqMess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startConsumer();				
	}
	
	public MirrorTask(List <Attribute> atts, TaskHandler handler, boolean strict, 
			boolean async, String nodeId, int fpcId ){
		
		this.atts=atts;
		this.handler=(MirrorTaskHandler) handler;
		this.fpcId=fpcId;
		this.nodeId=nodeId;
		
		queue = generateQueue();
		
		subscribeQueue();
		GetMessage reqMess = new GetMessage(atts, strict, async, -1, nodeId, this.fpcId);
		try {
			servMsgProd.sendGetMessage(reqMess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startConsumer();
	}
	
	
	protected final synchronized void processSample(Object[] sample) {
	        if (!running) {
	            return;
	        }
	        Sample output = pipeline.run(sample);
	        handler.data(this, output);
	    }
	
	@Override
	public List<Attribute> getAttributes() {
		// TODO Auto-generated method stub
		return atts;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return running;
	}
	
	@Override
	public void stop() {
		
		// Mando lo stop su queue
		running = false;
		
	}

	protected final synchronized void start() {
		
        if (hasStarted) {
            throw new IllegalStateException("Cannot start," +
                    "MirrorTask has already been started once");
        }
        running = true;
        hasStarted = true;
        
        //Iscrive la coda sul server 
        subscribeQueue();
        
        //Lancia un Consumer
        //il Consumer ogni volta che arrivano i dati lancia handler.data(this, dati);
        
        startConsumer();
        
    }
	
	private String generateQueue() {
		//To do 
		String fpcString = ((Integer)fpcId).toString();
		String queue = "queue" + nodeId + fpcString;
		return queue;
		
    }
	
	public void subscribeQueue(){
		
	}
	
	public void startConsumer(){
		//To do 
		handler.data(this, null);
		
	}
	
}

