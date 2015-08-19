package org.dei.perla.aggregator.pms.types;

import java.io.Serializable;

import org.dei.perla.core.sample.Sample;

public class DataMessage implements Serializable{

	private Sample sample;

	public DataMessage (Sample sample){
		this.sample = sample;
	}
	
	public Sample getSample() {
		return sample;
	}

	
}
