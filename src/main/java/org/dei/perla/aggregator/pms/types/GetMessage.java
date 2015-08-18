package org.dei.perla.aggregator.pms.types;

import java.io.Serializable;
import java.util.List;
import org.dei.perla.core.sample.Attribute;


public class GetMessage implements Serializable{
	
	private String nodeId;
	private int fpcId;
	private boolean async;
	private boolean strict;
	private List<Attribute> attributes;
	private long periodMs;
	
	public GetMessage(List<Attribute> attributes, boolean strict, boolean async, 
			long periodMs, String nodeId, int fpcId){
		this.attributes = attributes;
		this.strict = strict;
		this.async = async;
		this.periodMs = periodMs;
		this.nodeId = nodeId;
		this.fpcId=fpcId;
		
	}

	public String getNodeId(){
		return nodeId;
	}

	public int getFpcId() {
		return fpcId;
	}

	public boolean isAsync() {
		return async;
	}

	public boolean isStrict() {
		return strict;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public long getPeriodMs() {
		return periodMs;
	}
}
