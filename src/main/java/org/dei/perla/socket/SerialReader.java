package org.dei.perla.socket;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialReader {
	
	/**
	 * Classe per la comunicazione seriale con i sensori
	 * @param args
	 */
	
	public static void main(String args[]){
		SerialPort serialPort = new SerialPort("/dev/ttyACM0");
		
		//sudo chmod 666 /dev/ttyACM0
		
		try {
			serialPort.openPort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Open serial port
		try {
			serialPort.setParams(9600, 8, 1, 0);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Set params.
		while(true) {
		    byte[] buffer;
			try {
				//buffer = serialPort.readBytes(4);
				
				String input = serialPort.readString();
				// String input=serialPort.readHexString();
			
				if(input==null){
					//System.out.println("null");
					continue;
				}
				if(!input.isEmpty()) {
				 
				 System.out.print(input);      	   
				         
			}
				
				   				  
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			
		}
		
	}
	
}
