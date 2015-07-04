package org.dei.perla.aggregator.pms.server;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.dei.perla.aggregator.pms.types.AddFpcMessage;
import org.dei.perla.aggregator.pms.types.DataMessage;



public class ServerMessageListener implements MessageListener{

	private String ident = null;

	  public ServerMessageListener() {
	    ident = "listener";
	  }

	  
	@Override
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
		    	  
	    	  	if (((ObjectMessage) msg).getObject() instanceof AddFpcMessage){
    	  			//Operazioni per creare un MirrorFpc
	    	  		AddFpcMessage message = (AddFpcMessage) ((ObjectMessage) msg).getObject();
	    	  		message.getAttributesMap();
    	  			
    	  			
    	  			
	    	  	}
	    	  	if (((ObjectMessage) msg).getObject() instanceof DataMessage){
    	  			DataMessage message = (DataMessage) ((ObjectMessage) msg).getObject();
	    	  	}
		    	
		      }
		      else {
		    	  System.out.println("error");
		      }
		    } catch (JMSException jE) {
		      System.err.println("Exception in listener: " + jE);
		    }
		
	}

}
