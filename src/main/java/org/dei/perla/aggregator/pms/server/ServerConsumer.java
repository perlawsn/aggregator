package org.dei.perla.aggregator.pms.server;

	import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

	import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dei.perla.aggregator.pms.node.AggregatorSystem;
import org.dei.perla.aggregator.pms.types.AddFpcMessage;
import org.dei.perla.aggregator.pms.types.DataMessage;
import org.dei.perla.aggregator.pms.types.QueryMessage;
import org.dei.perla.core.PerLaSystem;

	public class ServerConsumer  implements Runnable {

		private PerLaSystem aggSystem = null;
		private  javax.naming.Context ictx = null;
		Destination dest = null;
		ConnectionFactory cf = null;
		public ServerConsumer (PerLaSystem aggSystem) {
			
			this.aggSystem = aggSystem;
			
		}
		
		@Override
		public void run() {
			System.out.println("Listens to " );
		    Properties p = new Properties();
		    p.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
		    p.setProperty("java.naming.factory.host", "localhost");
		    p.setProperty("java.naming.factory.port", "16400");
		    
			try {
				ictx = new InitialContext(p);
				
				dest = (Destination) ictx.lookup("queue");
				cf = (ConnectionFactory) ictx.lookup("cf");
				    ictx.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			
		    Connection cnx;
			try {
				cnx = cf.createConnection();
				Session sess = cnx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			    MessageConsumer recv = sess.createConsumer(dest);

			    recv.setMessageListener(new MsgListener());
			    cnx.start();
			    System.in.read();
			    cnx.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    

		    System.out.println("Consumer closed.");

			
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
			    
			    	  		if (((ObjectMessage) msg).getObject() instanceof AddFpcMessage){
			    	  		AddFpcMessage message = (AddFpcMessage) ((ObjectMessage) msg).getObject();
			    	  		//Attiva l'aggiunta dell'fpc 
			    	  		message.getNodeId();
			    	  		}
			    	  		if (((ObjectMessage) msg).getObject() instanceof DataMessage){
				    	  		DataMessage message = (DataMessage) ((ObjectMessage) msg).getObject();
				    	  		//riceve dati	
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
		


	
	

