package org.dei.perla.socket;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import jssc.SerialPort;
import jssc.SerialPortException;

public class InsertionDatabase {
	
	
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
		
		String sample="";
		Connection con = null;
		Statement cmd = null;
		
		MySqlPerLa myPerla = new MySqlPerLa();
        String messageString="";
        
        try {
			con = myPerla.connect("jdbc:mysql://localhost/perla_database");
			cmd = con.createStatement();
			
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				
				 
					if (input.equals("\n")){
						//System.out.println(sample);
						//System.out.println(sample.split("_")[0]);
						//System.out.println(sample.split("_")[1]);
						//System.out.println(sample.split("_")[2]);
						//System.out.println(sample.split("_")[3]);
						//System.out.println(sample.split("_")[4]);
						//System.out.println("query");
						String timeStamp = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date(System.currentTimeMillis()));
						 
						String insertingQuery="INSERT INTO st_el(temperature, humidity, axeX, axeY, axeZ, date) "
			                  		+ "VALUES("+"'"+sample.split("_")[0]+"',"
			                  				+"'"+sample.split("_")[1]+"', "
			                  				+"'"+sample.split("_")[2] +"', "
			                  				+"'"+sample.split("_")[3]+ "', "
			                  				+"'"+sample.split("_")[4]+ "', "
			                  				+"'"+timeStamp+"' "
			                  						+ ");";
						
						System.out.println(insertingQuery);
						
						try {
							cmd.executeUpdate(insertingQuery);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						sample="";
					}
					else{
						sample=sample.concat(input);
					}
					
					
				}
				
				   				  
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			
		}
		
	}
	
	public static void insertInDb(String input){
		if (input.equals("\n")){
			
		}else{
		System.out.print(input);
		}
		
	}
}
