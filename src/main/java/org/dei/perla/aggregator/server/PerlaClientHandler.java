package org.dei.perla.aggregator.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PerlaClientHandler implements Runnable{
	
	private Socket socket;
	public PerlaClientHandler (Socket	 socket	){
		this.socket	=  socket;
	}

	@Override
	public void run() {
		try	{
            Scanner in	= new Scanner(socket.getInputStream());
            PrintWriter	out	= new PrintWriter(socket.getOutputStream());
		//leggo	 e	scrivo 	nella	connessione	finche'  non ricevo "quit"
		while(true)
		{
			String line	= in.nextLine();
			if(line.equals("quit"))	{
				break;
			}else if (line.contains("Ciao")){
				out.println("Ciao, io sono il server");
				out.flush();
				
			}else if (line.contains("sensore")){
				out.println("inviami il descrittore");
				out.flush();
			}
			else
		    	{
					System.out.println("Received " + line);
					out.println("Received:  "+ line);
					out.flush();
		    	}
		}
		//chiudo gli stream  e  il  socket 
		in.close();
		out.close();
		
		socket.close();
	}
	catch(IOException e	)
		{
		System.err.println(e.getMessage());
		}
		
		
	}
 
}
