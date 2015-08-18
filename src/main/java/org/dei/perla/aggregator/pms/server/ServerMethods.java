package org.dei.perla.aggregator.pms.server;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.dei.perla.aggregator.pms.types.GetMessage;
import org.objectweb.joram.client.jms.Queue;


public class ServerMethods {
	
	public void sendGetMessage(GetMessage reqMsg) throws Exception{
		Properties p = new Properties();
		p.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
	    p.setProperty("java.naming.factory.host", "localhost");
	    p.setProperty("java.naming.factory.port", "16400");
	    javax.naming.Context jndiCtx = new InitialContext(p);
	    Destination queue = (Queue) jndiCtx.lookup("AggrQueue" + reqMsg.getNodeId());
	    ConnectionFactory cf = (ConnectionFactory) jndiCtx.lookup("cf");
	    jndiCtx.close();
	    Connection cnx = cf.createConnection();
	    Session sess = cnx.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    MessageProducer producer = sess.createProducer(queue);
	    ObjectMessage omsg = sess.createObjectMessage(reqMsg);
	    producer.send(omsg);
	    cnx.close();
	}

	
	
}
