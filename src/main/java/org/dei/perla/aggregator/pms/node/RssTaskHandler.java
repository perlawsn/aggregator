package org.dei.perla.aggregator.pms.node;

import org.dei.perla.core.fpc.Sample;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.web.aggr.types.DataMessage;

public class RssTaskHandler implements TaskHandler{
	private AggregatorMethods aggrMet;
	private String queue;
	private Task task;
	@Override
	public void complete(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void data(Task task, Sample sample) {
		DataMessage dataMessage = new DataMessage();
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
