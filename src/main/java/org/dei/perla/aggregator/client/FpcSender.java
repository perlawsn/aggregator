package org.dei.perla.aggregator.client;

import java.util.Collection;


import org.dei.perla.core.sample.Attribute;

public class FpcSender {

	private Collection<Attribute> atts;
	private String fpcString;
	public FpcSender(Collection<Attribute> atts){
		this.atts=atts;
		fpcString = generateFpcString();
	}
	
	private String generateFpcString(){
		
		return null;
	}
	
	public String getFpcString(){
		return fpcString;
	}
}
