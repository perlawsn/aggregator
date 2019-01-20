package org.dei.perla.aggregator.pms.server;

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

/** @documentazione gennaio 2019
* Per connettersi al server Joram, devono essere inizializzata una classe che
* dica al sistema l'indirizzo di Joram. In questo caso decidiamo che il server Joram risieda
* sul server PerLa. Sono logicamente due entità distinte, ma per comodità li teniamo su 
* un'unica macchina. Un'applicazione professionale probabilmente vedrebbe il server Joram
* su una macchina dedicata. 
* In questo caso siamo nell'ambito del server PerLa, quindi l'hostname è localhost. 
*/

public class ServerAdmin {
	
	private Properties p = new Properties();
    private javax.naming.Context jndiCtx;
    
	public void createNodeContext() {
		
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
	    
	    while (!connected){
	    
	    Queue queue;
		try {
			queue = Queue.create("queue");
			queue.setFreeReading();
			queue.setFreeWriting();
			jndiCtx.bind("queue", queue);
			jndiCtx.bind("cf", cf);
		} catch (ConnectException | AdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e){
			break;
		}
	    
			
		try {
			jndiCtx.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	AdminModule.disconnect();
	    System.out.println("Admin closed.");
	    connected = true;
	    }
	    
	   
}
	
	public static void main(String args[]){
		ServerAdmin servAdmin;
		servAdmin=new ServerAdmin();
		servAdmin.createNodeContext();
	}
	
}
