package org.dei.perla.channel.aggregator.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.dei.perla.core.descriptor.ChannelDescriptor;

@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class AggregatorChannelDescriptor extends ChannelDescriptor {
	
	int aggregatorId;
	
	public AggregatorChannelDescriptor(String id, int aggregatorId) {
		super(id);
		this.aggregatorId = aggregatorId;
	}

	public int getMoteId() {
		return aggregatorId;
	}


}
