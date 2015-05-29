package org.dei.perla.channel.aggregator.server;

import org.dei.perla.core.channel.AbstractChannel;
import org.dei.perla.core.channel.ChannelException;
import org.dei.perla.core.channel.IORequest;
import org.dei.perla.core.channel.Payload;

public class AggregatorChannel extends AbstractChannel  {

	public AggregatorChannel(int id) {
		super ("Aggregator channel" + id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Payload handleRequest(IORequest request) throws ChannelException,
			InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

}
