package org.dei.perla.aggregator.pms.node;

import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.web.aggr.types.DataMessage;
import org.dei.perla.core.fpc.Sample;

public class AggregatorTaskHandler implements TaskHandler {
	
	/**
	 * Classe tipica dell'FPC. Quando arriva un dato al FPC, viene richiamato il metodo data()
	 * che in questo caso manda in DataMessage al server Perla. 
	 */
	
	private AggregatorMethods aggrMet;
	private String queue;
	private Task task;
	
	public AggregatorTaskHandler(String queue){
		this.queue=queue;
	}
	
	protected void setTask(Task task) {
		this.task = task;
	}
	
	@Override
	public void complete(Task task) {
		// TODO Auto-generated method stub
	}

	@Override
	public void data(Task task, Sample sample) {
		//qui scatta l'invio
		
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
