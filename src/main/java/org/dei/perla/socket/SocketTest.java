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

public class SocketTest extends Thread {

    /**
     * Runs the server.
     */
	
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
                   
                    String messageString="";
					while(!end)
                    {
                        bytesRead = in.read(messageByte);
                        
                        messageString = new String(messageByte, 0, bytesRead);
                        
                        if (messageString.contains("DESCRIPTOR")){
                        	
                        	System.out.println("ARRIVATO UN DESCRITTORE: add FPC");
                        	System.out.println(messageString);
                        
                        
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