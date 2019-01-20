package org.dei.perla.socket;

import jssc.SerialPortList;

/**
 * Classe che stampa le porte seriali libere
 * @author cesco
 *
 */

public class SerialList {

	
	
	public static void main(String args[]){
		String[] portNames = SerialPortList.getPortNames();
		
		
		for(String s:portNames){
			System.out.println(s);
		}
	}
	
}
