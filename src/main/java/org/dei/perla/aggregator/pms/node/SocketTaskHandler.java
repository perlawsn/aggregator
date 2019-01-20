package org.dei.perla.aggregator.pms.node;

import org.dei.perla.core.fpc.Sample;
import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.web.aggr.types.DataMessage;

public class SocketTaskHandler implements TaskHandler {
	private AggregatorMethods aggrMet = new AggregatorMethods();
	/**
	 * Il metodo data lancia il metodo per inviare i dati al server. 
	 * In questo caso ho  previsto solo la possibilità che venga 
	 * utilizzato a bordo di un aggregatore/raspberry. 
	 * Al momento questo metodo non lo usiamo, è solo abbozzato.
	 * Come spiegato in SocketTest, ho inserito la connessione diretta al database remoto.
	 * 
	 */
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
