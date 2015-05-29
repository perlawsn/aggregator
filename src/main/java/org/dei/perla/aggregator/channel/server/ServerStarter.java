package org.dei.perla.aggregator.channel.server;

public class ServerStarter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AggregatorServerListener listener= new AggregatorServerListener(1377);
		listener.startServer();
	}

}
