package Prototype;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dei.perla.socket.MySqlPerLa;

import jssc.SerialPort;
import jssc.SerialPortException;

public class Receiver {
	
	
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
							
                    	                  
                    
             	 
				 System.out.print(input);      	   
				    String [] sample = input.split("_");
				    
                    String temperature = sample[0].split("'")[1];
                    String humidity = sample[1];
                    String axe1 = sample[2];
                    String axe2 = sample[3];
                    String axe3 = sample[4].split("'")[0];
                    String timeStamp = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date(System.currentTimeMillis()));
                    
                       
	
				
				  String insertingQuery="INSERT INTO st_el(temperature, humidity, axeX, axeY, axeZ, date) "
                  		+ "VALUES("+"'"+temperature+"',"
                  				+"'"+humidity+"', "
                  				+"'"+axe1 +"', "
                  				+"'"+axe2+ "', "
                  				+"'"+axe3+ "', "
                  				+"'"+timeStamp+"' "
                  						+ ");";
                  
                  try {
						cmd.executeUpdate(insertingQuery);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
						
				}
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			
		}
		
	}
	
}
