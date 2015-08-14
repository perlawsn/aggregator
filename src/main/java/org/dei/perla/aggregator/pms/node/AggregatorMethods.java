package org.dei.perla.aggregator.pms.node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import org.dei.perla.core.sample.Attribute;

public class AggregatorMethods {
	
	HashMap<String, String> map;
	static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwzxy";
    static Random rnd = new Random(System.currentTimeMillis());
    static private final int LENGHT = 4;
    
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

	
}
