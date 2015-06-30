package org.dei.perla.aggregator.pms.server;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;

import org.objectweb.joram.client.jms.Queue;
import org.objectweb.joram.client.jms.Topic;
import org.objectweb.joram.client.jms.admin.AdminModule;
import org.objectweb.joram.client.jms.admin.User;
import org.objectweb.joram.client.jms.tcp.TcpConnectionFactory;

public class ServerAdmin {
	
	public static void main(String[] args) throws 	Exception {
		ConnectionFactory cf = TcpConnectionFactory.create("localhost", 16010);
	    AdminModule.connect(cf, "root", "root");

	    Queue queue = Queue.create("serviceQueue");
	    queue.setFreeReading();
	    queue.setFreeWriting();
	    User.create("anonymous", "anonymous");
	    
	    QueueConnectionFactory qcf = TcpConnectionFactory.create("localhost", 16010);
	    
	    
	    Properties p = new Properties();
	    p.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
	    p.setProperty("java.naming.factory.host", "localhost");
	    p.setProperty("java.naming.factory.port", "16400");
	    javax.naming.Context jndiCtx = new javax.naming.InitialContext(p);
	    jndiCtx.bind("serviceQueue", queue);
	    jndiCtx.bind("serviceCf", cf);
	    jndiCtx.bind("serviceQcf", qcf);
	    
	    	    
	    jndiCtx.close();

	    AdminModule.disconnect();
	    System.out.println("Admin closed.");

}
}
