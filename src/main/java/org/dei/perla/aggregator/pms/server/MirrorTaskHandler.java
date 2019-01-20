package org.dei.perla.aggregator.pms.server;

import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.fpc.mysql.MySqlWrapper;
import org.dei.perla.core.fpc.Sample;

public class MirrorTaskHandler implements TaskHandler {

	/**
	 * Il MirrorTaskHandler, tramite il metodo data, inserisce i dati raccolti nel database
	 */
	private MySqlWrapper wrapper;
	
	public MirrorTaskHandler(MySqlWrapper wrapper){
		
		this.wrapper=wrapper;
		
	}
	
	@Override
	public void complete(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void data(Task task, Sample sample) {
		System.out.println("Sono in data");
		wrapper.getDbHandler().data(task, sample);
		
	}

	@Override
	public void error(Task task, Throwable cause) {
		// TODO Auto-generated method stub
		
	}

}
