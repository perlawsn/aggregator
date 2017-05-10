package org.dei.perla.socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SocketTest {

    /**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    
                    //a= [att1][att2][att3][att4]-[period]-[numerosample]
                    System.out.println("invio comando");
                    String a="1111-9-1";
                    out.println(a);
                    
                    int bytesRead;
                    byte[] messageByte = new byte[100];
                    InputStream clientInputStream = socket.getInputStream();
                    
                    DataInputStream in = new DataInputStream(clientInputStream);

                    boolean end = false;
                   
                    String messageString="";
					while(!end)
                    {
                        bytesRead = in.read(messageByte);
                        
                        messageString = new String(messageByte, 0, bytesRead);
                        
                        if (messageString.contains("descriptor")){
                        	
                        	//manda un messaggio JMS a Joram con un messaggio FPC
                        
                        }else if(messageString.contains("data")){
                        	
                        	//manda un messaggio JMS data message
                        	
                        	//
                        	
                        }
                      
                        
                        System.out.println("MESSAGE: " + messageString);
                        
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
    
    private void sendDataMessage(){
    	
    }
    
    private void sendFpcMessage(){
    	
    }
}