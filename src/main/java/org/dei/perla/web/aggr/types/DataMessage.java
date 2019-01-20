package org.dei.perla.web.aggr.types;

import java.io.Serializable;
import java.util.List;

import org.dei.perla.core.fpc.Attribute;
import org.dei.perla.core.fpc.Sample;

public class DataMessage implements Serializable{

	/**
	 * Questa classe viene riempita con l'elenco degli attributi e con i valori raccolti.
	 * Non ho usato una mappa, quindi sembra che attributi e valori siano slegati, ma 
	 * se tutto viene lanciato correttamente, il problema non si pone perché
	 * basta usare un indice comune.	
	 */
	private final List<Attribute> fields;
    private final Object[] values;

	public DataMessage (List<Attribute> fields, Object[] values){
		this.fields=fields;
		this.values=values;
	}
	
	public DataMessage(){
		fields=null;
		values=null;
	}
	
	

	public List<Attribute> getFields(){
		
		return fields;
	
	}
	
	public Object[] getValues(){
		
		return values;
		
	}
}
	
	