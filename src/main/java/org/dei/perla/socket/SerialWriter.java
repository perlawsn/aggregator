package org.dei.perla.socket;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialWriter {
	
	/**
	 * Classe per la comunicazione seriale con i sensori
	 * @param args
	 */
	
	
	public static void main(String args[]){
		
		SerialPort serialPort = new SerialPort("COM4");
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
		byte a=1;
		while(true){
		try {
			serialPort.writeByte(a);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

}
