package org.dei.perla.aggregator.pms.node;

import java.net.ConnectException;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.naming.NamingException;

import org.objectweb.joram.client.jms.Queue;
import org.objectweb.joram.client.jms.admin.AdminException;
import org.objectweb.joram.client.jms.admin.AdminModule;
import org.objectweb.joram.client.jms.admin.User;
import org.objectweb.joram.client.jms.tcp.TcpConnectionFactory;

public class AggregatorAdmin {
	
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
	    p.setProperty("java.naming.factory.host", "localhost"); //Remote host
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
	    tempNodeId = AggregatorMethods.generateId();
	    Queue queue;
		try {
			queue = Queue.create("AggrQueue"+ tempNodeId);
			queue.setFreeReading();
			queue.setFreeWriting();
			jndiCtx.bind("queue"+tempNodeId, queue);
			
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
	    
	    return tempNodeId;    
}

	
}
