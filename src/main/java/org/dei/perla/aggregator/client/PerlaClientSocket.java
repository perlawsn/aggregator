package org.dei.perla.aggregator.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PerlaClientSocket {
	
	private	String ip;
	
	private	int	port;
	
	public PerlaClientSocket(String ip, int port)
	{
	
	this.ip=ip;
	this.port=port;
	
	}

	
	
	public 	 void	startClient()throws IOException{
		
		Socket socket= new Socket(ip, port);
		System.out.println("Connection  established");
		
		Scanner socketIn=new Scanner(socket.getInputStream());
		PrintWriter	socketOut=new PrintWriter(socket.getOutputStream());
		Scanner stdin= new Scanner(System.in);
		
		try	{
			String code1="Hello Baby, I'm a Raspberry node";
			socketOut.println(code1);
			socketOut.flush();
			String socketLine2=socketIn.nextLine();
			System.out.println(socketLine2);
			while(true)	{
				String inputLine=stdin.nextLine();
				socketOut.println(inputLine);
				socketOut.flush();
				String socketLine=socketIn.nextLine();
				System.out.println(socketLine);
			}
	}
	catch(NoSuchElementException e)	{
		System.out.println("Connection  closed");
		}
	
		finally{
			stdin.close();
			socketIn.close();
			socketOut.close();
			socket.close();
			}
	}

	


public static void main(String[] args){

 PerlaClientSocket client = new PerlaClientSocket("127.0.0.1",1377);

 try {
	 client.startClient();
 }
 catch(IOException e ){
	 System.err.println(e.getMessage());

}
}
}