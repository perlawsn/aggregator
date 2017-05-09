package org.dei.perla.socket;

import jssc.SerialPortList;

public class SerialList {

	
	
	public static void main(String args[]){
		String[] portNames = SerialPortList.getPortNames();
		
		
		for(String s:portNames){
			System.out.println(s);
		}
	}
	
}
