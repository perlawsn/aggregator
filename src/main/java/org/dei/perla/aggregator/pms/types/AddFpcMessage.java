package org.dei.perla.aggregator.pms.types;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import org.dei.perla.core.sample.Attribute;

public class AddFpcMessage implements Serializable{
	
	String nodeId;
	int fpcId;
	HashMap<String, String> attributes;
	
	public AddFpcMessage(String nodeId, int fpcId, HashMap <String, String> attributes){
		this.nodeId=nodeId;
		this.fpcId = fpcId;		
		this.attributes = attributes;
	}
		
	public String getNodeId() {
		return nodeId;
	}
	
	public int getFpcId() {
		return fpcId;
	}
	
	public HashMap<String, String> getAttributesMap(){
		return attributes;
	}
   

	
	
	

}
