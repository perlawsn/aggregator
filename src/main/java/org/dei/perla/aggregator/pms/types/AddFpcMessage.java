package org.dei.perla.aggregator.pms.types;

import java.io.Serializable;
import java.util.HashMap;

public class AddFpcMessage implements Serializable{
	
	String nodeId;
	String FpcId;
	HashMap<String, String> AttributeMap;
	
	public AddFpcMessage(String FpcId){
		this.nodeId=nodeId;
		this.FpcId=FpcId;
		
	}
	
	public void setNodeId(String nodeId){
		this.nodeId=nodeId;
		
	}
	
	public String getNodeId() {
		return nodeId;
	}
	
	public String getFpcId() {
		return FpcId;
	}
		public HashMap<String, String> getAttributeMap() {
		return AttributeMap;
	}
	public void setAttributeMap(HashMap<String, String> attributeMap) {
		

	}
	
	
	

}
