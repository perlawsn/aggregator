package Prototype;

import org.dei.perla.socket.*;

public class Prototype {


	public static void main(String args[]){
		
		
		
		String query1 = "CREATE OUTPUT STREAM st_el(temperature,humidity,axeX,axeY,axeZ)"
					 + "SAMPLING EVERY 5 MINUTES"
					 + "EXECUTE IF EXISTS temperature,humidity,axeX,axeY,axeZ";
		
		String query2 = "CREATE OUTPUT STREAM feed()"
				+ "SAMPLING EVERY 5 MINUTES";
		
		RemoteFileReader fileReader = new RemoteFileReader();
		ParseQuery parser = new ParseQuery();
		int period = Integer.parseInt(parser.parseQueryTime(query1));
		System.out.println("PERIOD : "+period);
		//minuti				
		
		fileReader.setPeriod(period);
		
		  (new Thread(fileReader)).start();
		
		SocketTest socket = new SocketTest();
		 (new Thread(socket)).start();
	}
	
}