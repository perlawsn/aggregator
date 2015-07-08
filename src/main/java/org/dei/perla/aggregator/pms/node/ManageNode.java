package org.dei.perla.aggregator.pms.node;

import java.util.Properties;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.dei.perla.aggregator.pms.types.AddFpcMessage;
import org.objectweb.joram.client.jms.Queue;
public class ManageNode {
	 
	    static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwzxy";
	    static Random rnd = new Random(System.currentTimeMillis());
	    static private final int LENGHT = 4;
	 
	    public static String generateId() {
	        StringBuilder sb = new StringBuilder(LENGHT);
	        for (int i = 0; i < LENGHT; i++) {
	            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
	        }
	        return sb.toString();
	    }

				
		
}
