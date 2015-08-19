package org.dei.perla.aggregator.pms.node;

import org.dei.perla.aggregator.pms.types.DataMessage;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.core.sample.Sample;

public class AggregatorTaskHandler implements TaskHandler {

	@Override
	public void complete(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void data(Task task, Sample sample) {
		//qui scatta l'invio
		
		DataMessage dataMessage = new DataMessage(sample);
	}

	@Override
	public void error(Task task, Throwable cause) {
		// TODO Auto-generated method stub
		
	}

}
