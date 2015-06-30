package org.dei.perla.aggregator.pms.node;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.dei.perla.aggregator.pms.types.AddFpcMessage;
import org.objectweb.joram.client.jms.Queue;
import org.objectweb.joram.client.jms.admin.AdminModule;
import org.objectweb.joram.client.jms.admin.User;
import org.objectweb.joram.client.jms.tcp.TcpConnectionFactory;

public class NodeAdmin {
	
    private String nodeId;
    private Properties p = new Properties();
    
	public void createNodeContext() throws 	Exception {
		
		ConnectionFactory cf = TcpConnectionFactory.create("localhost", 16010);
	    AdminModule.connect(cf, "root", "root");
	    User.create("anonymous", "anonymous");
	    QueueConnectionFactory qcf = TcpConnectionFactory.create("localhost", 16010);
	    p.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
	    p.setProperty("java.naming.factory.host", "localhost");
	    p.setProperty("java.naming.factory.port", "16400");
	    javax.naming.Context jndiCtx = new javax.naming.InitialContext(p);
	    nodeId = ManageNode.generateId();
	    Queue queue = Queue.create("queue"+ nodeId);
	    queue.setFreeReading();
	    queue.setFreeWriting();
	    jndiCtx.bind("queue"+nodeId, queue);
	    jndiCtx.bind("cf"+nodeId, cf);
	    jndiCtx.bind("qcf"+nodeId, qcf);
    	jndiCtx.close();
    	AdminModule.disconnect();
	    System.out.println("Admin closed.");
	    	    
}
	public void sendFpcMessage(AddFpcMessage fpc) throws Exception{
		fpc.setNodeId(nodeId);
		p.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
	    p.setProperty("java.naming.factory.host", "localhost");
	    p.setProperty("java.naming.factory.port", "16400");
	    javax.naming.Context jndiCtx = new InitialContext(p);
	    Destination queue = (Queue) jndiCtx.lookup("serviceQueue");
	    ConnectionFactory cf = (ConnectionFactory) jndiCtx.lookup("cf");
	    jndiCtx.close();
	    Connection cnx = cf.createConnection();
	    Session sess = cnx.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    MessageProducer producer = sess.createProducer(queue);
	    ObjectMessage omsg = sess.createObjectMessage(fpc);
	    producer.send(omsg);
	    cnx.close();
	}
	
}
