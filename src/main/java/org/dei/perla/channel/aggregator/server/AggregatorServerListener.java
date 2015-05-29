package org.dei.perla.channel.aggregator.server;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class AggregatorServerListener  {
	
	private	int  port;
	
	public AggregatorServerListener (int port)	{
		this.port= port;
		}
	
	public void	startServer(){
		ExecutorService executor=Executors.newCachedThreadPool();
		ServerSocket serverSocket;
		
		try	{
			serverSocket=new ServerSocket(port);
			}
		catch(IOException e){
			System.err.println(e.getMessage());
			// 	porta non disponibile 
			return ;
		}
		
	System.out.println("Server  ready");
	
	while(true)
		{
			try
			{
				Socket socket	=serverSocket.accept();
			executor.submit(new PerlaClientHandler(socket));
			}
			catch(IOException e){
				break;

	//entrerei qui  se	serverSocket venisse chiuso
				
			}
	}
	
	executor.shutdown();
	}
	
	public void startListener(){
		this.startServer();
	}
}

 class PerlaClientHandler implements Runnable{
	
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

	
	
	

