package org.dei.perla.aggregator.pms.server;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Collection;
import java.util.List;

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

import org.dei.perla.core.fpc.Attribute;
import org.dei.perla.core.fpc.Sample;
import org.dei.perla.core.fpc.TaskHandler;
import org.dei.perla.core.registry.DuplicateDeviceIDException;
import org.dei.perla.core.registry.TreeRegistry;
import org.dei.perla.web.aggr.types.*;
import org.dei.perla.fpc.mysql.*;
import org.dei.perla.fpc.mysql.MySqlWrapper.WrongCorrespondenceException;

public class ServerConsumer implements Runnable {

	private javax.naming.Context ictx = null;
	private Destination dest = null;
	private ConnectionFactory cf = null;
	private TreeRegistry registry;
	private String port;
	MirrorTaskHandler mth;
	public ServerConsumer(TreeRegistry registry, String port) {

		this.registry = registry;
		this.port=port;
	}

	@Override
	public void run() {
		System.out.println("Listens to ");
		Properties p = new Properties();
		p.setProperty("java.naming.factory.initial","fr.dyade.aaa.jndi2.client.NamingContextFactory");
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

				Enumeration e = msg.getPropertyNames();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = msg.getStringProperty(key);

				}

				if (msg instanceof TextMessage) {
					System.out.println(((TextMessage) msg).getText());
				} else if (msg instanceof ObjectMessage) {
					//Arriva messaggio di un nuovo FPC
					if (((ObjectMessage) msg).getObject() instanceof AddFpcMessage) {
						AddFpcMessage message = (AddFpcMessage) ((ObjectMessage) msg)
								.getObject();
						
						System.out.println("Fpc received");
						
						// Attiva l'aggiunta dell'fpc

						MirrorFpc newFpc = new MirrorFpc(message.getFpcId(),
								message.getNodeId(), "mirror",
								message.getAttributesMap());
						
						MySqlWrapper nw= new MySqlWrapper(newFpc);
						mth=new MirrorTaskHandler(nw);
						List<Attribute> attr =  newFpc.getAttributes();
						nw.get(attr, mth, "prova");
						
						
						MirrorTask task = (MirrorTask) newFpc.get(attr, mth);
						
						
						 try {
							registry.add(newFpc);
						} catch (DuplicateDeviceIDException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						

					}
					//Arrivano dati dal sistema pervasivo
					if (((ObjectMessage) msg).getObject() instanceof DataMessage) {
						DataMessage message = (DataMessage) ((ObjectMessage) msg)
								.getObject();
						Sample sample=new Sample(message.getFields(), message.getValues());
						mth.data(task, sample);
						Map<String, String> m =convert(sample);
						m.get("id");
						registry.get(Integer.parseInt(m.get("id")));
					}
					
					if (((ObjectMessage)msg).getObject()instanceof QueryMessage){
						
						//si lancia il parser
						//si controllano gli fpc coinvolti
						//si controllano gli aggregatori coinvolti
						
						
					}

				} else {
					System.out.println("Error");
				}
			} catch (JMSException jE) {
				System.err.println("Exception in listener: " + jE);
			} catch (WrongCorrespondenceException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
		}
	}

	private Map<String, String> convert(Sample s) {
        Map<String, String> m = new HashMap<>();
        s.fields().forEach((a) -> {
            String id = a.getId();
            Object value = s.getValue(id);
            m.put(id, value.toString());
            
            System.out.println ("Preso: "+id+" : " +value.toString());
            
        });
        return m;
    }
}
