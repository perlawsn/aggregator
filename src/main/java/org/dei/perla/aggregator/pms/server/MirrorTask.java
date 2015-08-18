package org.dei.perla.aggregator.pms.server;

import java.util.List;
import java.util.Random;

import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.core.sample.Attribute;
import org.dei.perla.core.sample.Sample;
import org.dei.perla.core.sample.SamplePipeline;

public class MirrorTask implements Task{
	
	private final List<Attribute> atts;
	private final MirrorTaskHandler handler;
	private boolean hasStarted = false;
    private boolean running = false;
    private SamplePipeline pipeline;
    static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwzxy";
    static Random rnd = new Random(System.currentTimeMillis());
    static private final int LENGHT = 4;
	
	public MirrorTask(List <Attribute> atts, TaskHandler handler){
		this.atts=atts;
		this.handler=(MirrorTaskHandler) handler;
		
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
        
        //Iscrive le code sul server 
        subscribeQueue();
        
        //Lancia un Consumer
        //il Consumer ogni volta che arrivano i dati lancia handler.data(this, dati);
        
    }
	
	public static String generateId() {
        StringBuilder sb = new StringBuilder(LENGHT);
        for (int i = 0; i < LENGHT; i++) {
            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
	
	public void subscribeQueue(){
	}
	
}

