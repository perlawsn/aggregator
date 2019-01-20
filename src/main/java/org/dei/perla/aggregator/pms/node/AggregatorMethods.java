package org.dei.perla.aggregator.pms.node;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.dei.perla.core.fpc.Attribute;
import org.dei.perla.web.aggr.types.AddFpcMessage;
import org.dei.perla.web.aggr.types.DataMessage;
import org.objectweb.joram.client.jms.Queue;

public class AggregatorMethods {
	
	HashMap<String, String> map;
	static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwzxy";
    static Random rnd = new Random(System.currentTimeMillis());
    static final int LENGHT = 4;
    private String dataQueue;
    
    /**
     * Il costruttore riceve come parametro la coda dedicata a questo aggregator/raspberry.
     * @param dataQueue
     */
    public AggregatorMethods(String dataQueue){
    	this.dataQueue=dataQueue;
    	
    }
    
    public AggregatorMethods(){
    	
    }
    
    /**
     * Viene generata la lista degli attributi dell'FPC. Il metodo riceve appunto 
     * una lista di attributi, che sono quelli dell'FPC creato sul sistema aggregator/raspberry.
     * Verranno quindi inviati al server in modo da poter creare un MirrorFPC corrispondente
     * all'FPC contenuto sull'aggregator.
     * @param attributeList
     * @return
     */
    public HashMap<String, String> generateListAttributes(Collection<Attribute> attributeList){
		
		for(Attribute att:attributeList){
			map.put(att.getId(),att.getType().getId());
					
		}
		
		return map;
	}
	
    public static String generateId() {
        StringBuilder sb = new StringBuilder(LENGHT);
        for (int i = 0; i < LENGHT; i++) {
            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    /**
     * Questo metodo manda al server un messaggio contenente le informazioni degli FPC appena creati.
     * NEl campo dove ho scritto ip/dns va messo l'ip del server. Ho lasciato la porta.
     * Il parametro "serverqueue" indica il nome della coda del server. In pratica
     * la Destination è la queue (coda) con il nome serverqueue, che viene ricercata tramite 
     * il metodo lookup.
     * 
     * @param fpc
     * @throws Exception
     */
    public void sendFpcMessage(AddFpcMessage fpc) throws Exception{
    	
    	clone(fpc);
    	
		Properties p = new Properties();
		p.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
	    p.setProperty("java.naming.factory.host", "ip/dns");
	    p.setProperty("java.naming.factory.port", "16500");
	    javax.naming.Context jndiCtx = new InitialContext(p);
	    Destination queue = (Queue) jndiCtx.lookup("serverqueue");
	    ConnectionFactory cf = (ConnectionFactory) jndiCtx.lookup("cf");
	    jndiCtx.close();
	    Connection cnx = cf.createConnection();
	    Session sess = cnx.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    MessageProducer producer = sess.createProducer(queue);
	    ObjectMessage omsg = sess.createObjectMessage(fpc);
	    producer.send(omsg);
	    cnx.close();
	}
    
    /**
     * Questo metodo manda al server un messaggio contenente le informazioni dei dati raccolti.
     * NEl campo dove ho scritto ip/dns va messo l'ip del server. Ho lasciato la porta.
     * Il parametro "serverqueue" indica il nome della coda del server. In pratica
     * la Destination è la queue (coda) con il nome serverqueue, che viene ricercata tramite 
     * il metodo lookup.
     * 
     */
    
    public void sendDataMessage(DataMessage message) throws Exception{
    	Properties p = new Properties();
		p.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
	    p.setProperty("java.naming.factory.host", "localhost");
	    p.setProperty("java.naming.factory.port", "16500");
	    javax.naming.Context jndiCtx = new InitialContext(p);
	    Destination queue = (Queue) jndiCtx.lookup(dataQueue);
	    ConnectionFactory cf = (ConnectionFactory) jndiCtx.lookup("cf");
	    jndiCtx.close();
	    Connection cnx = cf.createConnection();
	    Session sess = cnx.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    MessageProducer producer = sess.createProducer(queue);
	    ObjectMessage omsg = sess.createObjectMessage(message);
	    producer.send(omsg);
	    cnx.close();
	}
    
    public static final Object clone(Serializable in) {
        try {
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ObjectOutputStream outStream = new ObjectOutputStream(byteOutStream);
            outStream.writeObject(in);
            ByteArrayInputStream byteInStream =
                new ByteArrayInputStream(byteOutStream.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteInStream);
            return inStream.readObject();
        } catch (OptionalDataException e) {
         throw new RuntimeException("Optional data found. " + e.getMessage()); //$NON-NLS-1$
        } catch (StreamCorruptedException e) {
         throw new RuntimeException("Serialized object got corrupted. " + e.getMessage()); //$NON-NLS-1$
        } catch (ClassNotFoundException e) {
         throw new RuntimeException("A class could not be found during deserialization. " + e.getMessage()); //$NON-NLS-1$
        } catch (NotSerializableException ex) {
            ex.printStackTrace();
         throw new IllegalArgumentException("Object is not serializable: " + ex.getMessage()); //$NON-NLS-1$
        } catch (IOException e) {
         throw new RuntimeException("IO operation failed during serialization. " + e.getMessage()); //$NON-NLS-1$
        }
    }
    
	
}
