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

/**
 * 
 * @documentazione gennaio 2019
 * Per connettersi al server Joram, devono essere inizializzata una classe che
 * dica al sistema l'indirizzo di Joram. In questo caso decidiamo che il server Joram risieda
 * sul server PerLa. Sono logicamente due entità distinte, ma per comodità li teniamo su 
 * un'unica macchina. Un'applicazione professionale probabilmente vedrebbe il server Joram
 * su una macchina dedicata. 
 * In questo caso però siamo nell'ambito dell'aggregatore (che è il Raspberry), quindi dobbiamo inserire 
 * il server remoto. 
 */


public class AggregatorAdmin {
	
    private Properties p = new Properties();
    /**
     * Inizializza il contesto del server (non c'entra con il contesto di PerLa :-) ). Le classi 
     * javax sono mututate da J2ee.
     */
    private javax.naming.Context jndiCtx;
    private int port1;
    private String port2;
    
    /**
     * Il costruttore riceve due porte.
     * @param port1
     * @param port2
     */
    public AggregatorAdmin(int port1, String port2 ){
    	this.port1=port1;
    	this.port2=port2;
    
    }
    
	public String createNodeContext() {
		
		/**
		 * Viene inizializzato il server con ip/dns e porta. 
		 * L'utente è di default ed è l'utente root di Linux
		 */
		
		ConnectionFactory cf = TcpConnectionFactory.create("ip/dns", port1);
	    try {
			AdminModule.connect(cf, "root", "root");
			User.create("anonymous", "anonymous");
		} catch (ConnectException | AdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    /**
	     * Viene creata la Queue Connection Factory che è il componente che crea le code 
	     * che servono per il modello publish subscribe.
	     */
	    QueueConnectionFactory qcf = TcpConnectionFactory.create("localhost", port1);
	    p.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
	    p.setProperty("java.naming.factory.host", "ip/dns"); //Remote host
	    p.setProperty("java.naming.factory.port", port2);
	    
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
	    /**
	     * Viene creata la coda associata a questo aggregatore.
	     */
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
