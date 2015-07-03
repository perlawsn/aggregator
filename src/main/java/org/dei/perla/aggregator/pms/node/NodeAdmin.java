package org.dei.perla.aggregator.pms.node;

import java.net.ConnectException;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dei.perla.aggregator.pms.types.AddFpcMessage;
import org.objectweb.joram.client.jms.Queue;
import org.objectweb.joram.client.jms.admin.AdminException;
import org.objectweb.joram.client.jms.admin.AdminModule;
import org.objectweb.joram.client.jms.admin.User;
import org.objectweb.joram.client.jms.tcp.TcpConnectionFactory;

public class NodeAdmin {
	
    private String nodeId;
    private Properties p = new Properties();
    private javax.naming.Context jndiCtx;
	public String createNodeContext() {
		
		ConnectionFactory cf = TcpConnectionFactory.create("localhost", 16010);
	    try {
			AdminModule.connect(cf, "root", "root");
			User.create("anonymous", "anonymous");
		} catch (ConnectException | AdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    QueueConnectionFactory qcf = TcpConnectionFactory.create("localhost", 16010);
	    p.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
	    p.setProperty("java.naming.factory.host", "localhost");
	    p.setProperty("java.naming.factory.port", "16400");
	    
		try {
			jndiCtx = new javax.naming.InitialContext(p);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    boolean connected=false;
	    String tempNodeId =null;
	    while (!connected){
	    tempNodeId = ManageNode.generateId();
	    Queue queue;
		try {
			queue = Queue.create("queue"+ tempNodeId);
			queue.setFreeReading();
			queue.setFreeWriting();
			jndiCtx.bind("queue"+tempNodeId, queue);
			jndiCtx.bind("cf"+tempNodeId, cf);
			jndiCtx.bind("qcf"+tempNodeId, qcf);
		    jndiCtx.close();
		} catch (ConnectException | AdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e){
			continue;
		}
	      
    	AdminModule.disconnect();
	    System.out.println("Admin closed.");
	    connected = true;
	    }
	    nodeId=tempNodeId;
	    return tempNodeId;	    
}
	public void sendFpcMessage(AddFpcMessage fpc) throws Exception{
		
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
