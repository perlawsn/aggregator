package org.dei.perla.aggregator.pms.node;

import java.util.List;

import org.dei.perla.core.fpc.Task;
import org.dei.perla.core.fpc.Attribute;

public class AggregatorTask implements Task {

	/**
	 * in questo caso non la usiamo
	 */
	@Override
	public List<Attribute> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
