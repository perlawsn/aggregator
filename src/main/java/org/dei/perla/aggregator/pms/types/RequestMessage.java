package org.dei.perla.aggregator.pms.types;

import java.io.Serializable;
import java.util.List;
import org.dei.perla.core.sample.Attribute;


public class RequestMessage implements Serializable{
	
	private String nodeId;
	private int fpcId;
	private boolean async;
	private boolean strict;
	private List<Attribute> attributes;
	private long periodMs;
	
	public RequestMessage(List<Attribute> attributes, boolean strict, boolean async, long periodMs, String nodeId){
		this.attributes = attributes;
		this.strict = strict;
		this.async = async;
		this.periodMs = periodMs;
		this.nodeId = nodeId;
		
	}

	public String getNodeId(){
		return nodeId;
	}
}
