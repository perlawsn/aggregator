package org.dei.perla.aggregator.pms.node;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.dei.perla.aggregator.pms.node.system.AggregatorSystem;
import org.dei.perla.aggregator.pms.types.QueryMessage;

public class AggregatorConsumer implements Runnable {

	private AggregatorSystem aggSystem = null;
	
	public AggregatorConsumer (AggregatorSystem aggSystem) {
		
		this.aggSystem = aggSystem;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


public class MsgListener implements MessageListener {
	
	  public void onMessage(Message msg) {
		    try {
		      Destination destination = msg.getJMSDestination();
		      Destination replyTo = msg.getJMSReplyTo();

		    
		      Enumeration e = msg.getPropertyNames();
		      while (e.hasMoreElements()) {
		        String key = (String) e.nextElement();
		        String value = msg.getStringProperty(key);
		       
		      }

		      if (msg instanceof TextMessage) {
		        System.out.println(((TextMessage) msg).getText());
		      } else if (msg instanceof ObjectMessage) {
		    
		    	  		if (((ObjectMessage) msg).getObject() instanceof QueryMessage){
		    	  		QueryMessage message = (QueryMessage) ((ObjectMessage) msg).getObject();
		    	  		//Inizia lo smistamento delle query	
		    	  		}
		    	
		      }
		      else {
		    	  System.out.println("Error");
		      }
		    } catch (JMSException jE) {
		      System.err.println("Exception in listener: " + jE);
		    }
		  }
}



}
	

