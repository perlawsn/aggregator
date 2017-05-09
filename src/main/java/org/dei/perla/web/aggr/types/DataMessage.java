package org.dei.perla.web.aggr.types;

import java.io.Serializable;
import java.util.List;

import org.dei.perla.core.fpc.Attribute;
import org.dei.perla.core.fpc.Sample;

public class DataMessage implements Serializable{

		
	private final List<Attribute> fields;
    private final Object[] values;

	public DataMessage (List<Attribute> fields, Object[] values){
		this.fields=fields;
		this.values=values;
	}
	
	

	public List<Attribute> getFields(){
		
		return fields;
	
	}
	
	public Object[] getValues(){
		
		return values;
		
	}
}
	
	