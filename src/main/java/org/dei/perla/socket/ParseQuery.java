package org.dei.perla.socket;

public class ParseQuery {
	
	public String parseQueryAttributes (String query){
		
		
		String query2=query.replace('(', '<').replace(')', '>');
		String a1=query2.split("<")[1];
		
		String attributes = a1.split(">")[0];
		String conditions = query.split("EXECUTE IF EXISTS")[1];
		
		
		System.out.println(attributes);
		
	
		return attributes;
	
	}
	
public String parseQueryConditions (String query){
		
		
		String query2=query.replace('(', '<').replace(')', '>');
		String a1=query2.split("<")[1];
		
		String attributes = a1.split(">")[0];
		String conditions = query.split("EXECUTE IF EXISTS")[1];
		
		
		
		System.out.println(conditions);
	
		return conditions;
	
	}
	
public String parseQueryTime(String query){
	
	String query2=query.split(" MINUTES")[0];
	String period=query2.split("EVERY ")[1];
	
	return period;
	
	
}
	

}
