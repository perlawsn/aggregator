package org.dei.perla.channel.aggregator.server;

import org.dei.perla.core.channel.Channel;
import org.dei.perla.core.channel.ChannelFactory;
import org.dei.perla.core.descriptor.ChannelDescriptor;
import org.dei.perla.core.descriptor.InvalidDeviceDescriptorException;

public class AggregatorChannelFactory implements ChannelFactory {

	@Override
	public Class<? extends ChannelDescriptor> acceptedChannelDescriptorClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Channel createChannel(ChannelDescriptor descriptor)
			throws InvalidDeviceDescriptorException {
		// TODO Auto-generated method stub
		return null;
	}

}
