package org.dei.perla.aggregator.pms.node;

import org.dei.perla.aggregator.pms.types.DataMessage;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.core.sample.Sample;

public class AggregatorTaskHandler implements TaskHandler {
	AggregatorMethods aggrMet;
	
	String queue ;
	
	public AggregatorTaskHandler(String queue){
		this.queue=queue;
	}
	
	@Override
	public void complete(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void data(Task task, Sample sample) {
		//qui scatta l'invio
		
		DataMessage dataMessage = new DataMessage(sample);
		try {
			aggrMet.sendDataMessage(dataMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void error(Task task, Throwable cause) {
		// TODO Auto-generated method stub
		
	}

}
