package org.dei.perla.socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dei.perla.aggregator.pms.node.SocketTaskHandler;

public class SocketTest extends Thread {

    /**
     * Lancia il Socket.
     */
	
	SocketTaskHandler handler;
	
	public SocketTest(){
		
	}
	
	public SocketTest(SocketTaskHandler handler){
	this.handler=handler;	
	}
	
	public void run() {
		try {
			this.serverSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
    public void serverSocket() throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    
                    //a= [att1][att2][att3][att4][att5]-[period]-[numerosample]
                    System.out.println("invio comando");
                    String a="11111-9-1";
                    out.println(a);
                    
                    int bytesRead;
                    byte[] messageByte = new byte[200];
                    InputStream clientInputStream = socket.getInputStream();
                    
                    DataInputStream in = new DataInputStream(clientInputStream);

                                       
                    boolean end = false;
                  //Connection to database	
        			Connection con = null;
        			Statement cmd = null;
        			
        			MySqlPerLa myPerla = new MySqlPerLa();
                    String messageString="";
                    
                    try {
                    	/**
                    	 * Piccola nota: qui ho fatto una piccola deroga al modello di base
                    	 * Al posto di mandare il messaggio al server tramite JMS, il quale 
                    	 * server poi inserisce i dati nel database, ho pensato di mandare
                    	 * direttamente i dati al database.
                    	 * Se non piace, si può modificare il metodo data() di SocketTaskHandler
                    	 * e richiamarlo. Indico successivamente dove andrebbe fatto.
                    	 */
                    	
        				con = myPerla.connect("jdbc:mysql://<server>/perla_database");
        				cmd = con.createStatement();
        				
        				
        				
        				} catch (SQLException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
                    
                    int counter = 0;
                    String [] last5sample = {"0", "0", "0", "0", "0"};
					while(!end)
                    {
                        bytesRead = in.read(messageByte);
                        
                        messageString = new String(messageByte, 0, bytesRead);
                        
                        if (messageString.contains("DESCRIPTOR")){
                        	
                        	System.out.println("ARRIVATO UN DESCRITTORE: add FPC");
                        	System.out.println(messageString);
                        
                        	continue;
                        }
                      
                        
                        System.out.println("MESSAGE: " + messageString.replaceAll ("\r\n|\r|\n", " "));
                        String [] sample = messageString.split("_");
                        String temperature = sample[0];
                        
                        last5sample[counter] = temperature;
                        
                        String humidity = sample[1];
                        String axe1 = sample[2];
                        String axe2 = sample[3];
                        String axe3 = sample [4];
                        axe3=axe3.substring(0, 6);
                        String timeStamp = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date(System.currentTimeMillis()));
                        
                        /**
                         * handler.data() 
                         * per rispettare il modello perla andrebbe richiamato qui il metodo 
                         * data, per inviare al server un messaggio JMS 
                         * 
                         */
                        String insertingQuery="INSERT INTO st_el(temperature, humidity, axeX, axeY, axeZ, date) "
                          		+ "VALUES("+"'"+temperature+"',"
                          				+"'"+humidity+"', "
                          				+"'"+axe1 +"', "
                          				+"'"+axe2+ "', "
                          				+"'"+axe3+ "', "
                          				+"'"+timeStamp+"' "
                          						+ ");";
 
              
                        this.getMax(last5sample);
                        this.getAvg(last5sample);
                        System.out.println("##################");
                        
                        counter++;
                        if (counter==5){
                        	counter=0;
                        }
                        try {
    						cmd.executeUpdate(insertingQuery);
    					} catch (SQLException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
                        
                    }
                    
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
    
    
    private float  getMax(String[] last5sample){
    	
    	float massimo = Float.valueOf(last5sample[0]).floatValue();
    	for (int i=1; i<5; i++){
    		float current=Float.valueOf(last5sample[i]).floatValue();
    		if (current>massimo){
    			massimo = current;
    		}
    	}
    		System.out.println("Il massimo sample degli ultimi 5 minuti è "+massimo);
    		return massimo;
    	
    }
    
    private float  getAvg(String[] last5sample){
    	float sum=0;
    	for (int i=0; i<5; i++){
    		sum = sum + Float.valueOf(last5sample[i]).floatValue();
    	}
    	
    	float avg=sum/5;
    	System.out.println("Il valore medio dei sample degli ultimi 5 minuti è "+avg);
    	return avg;
    }
    
    
    
    
    private void sendDataMessage(){
    	
    }
    
    private void sendFpcMessage(){
    	
    }
}