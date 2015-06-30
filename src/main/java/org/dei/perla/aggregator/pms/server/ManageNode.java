package org.dei.perla.aggregator.pms.server;

import java.util.Random;
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
