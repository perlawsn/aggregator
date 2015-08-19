package org.dei.perla.aggregator.pms.types;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import org.dei.perla.core.sample.Attribute;

public class AddFpcMessage implements Serializable{
	
	private String nodeId;
	private int fpcId;
	private Collection<Attribute> attributes;
	
	public AddFpcMessage(String nodeId, int fpcId, Collection<Attribute> attributes){
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
	
	public Collection<Attribute> getAttributesMap(){
		return attributes;
	}
   

	
	
	

}
