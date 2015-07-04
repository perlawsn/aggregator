package org.dei.perla.aggregator.pms.node.system;

import java.util.Collection;
import java.util.HashMap;

import org.dei.perla.core.sample.Attribute;

public class AggregatorMethods {
	
	HashMap<String, String> map;
	
	public HashMap<String, String> generateListAttributes(Collection<Attribute> attributeList){
		
		for(Attribute att:attributeList){
			map.put(att.getId(),att.getType().getId());
					
		}
		
		return map;
	}
	
}
