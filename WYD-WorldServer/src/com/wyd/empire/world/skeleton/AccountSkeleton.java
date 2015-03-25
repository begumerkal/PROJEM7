package com.wyd.empire.world.skeleton;

import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.wyd.net.Connector;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.data.DataBeanFilter;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.protocol.s2s.S2SDecoder;
import com.wyd.protocol.s2s.S2SEncoder;

public class AccountSkeleton extends Connector implements IDataHandler {

	public AccountSkeleton(String id, InetSocketAddress address) {
		super(id, address);
	}

	@Override
	public void init() {
		this.connector.getFilterChain().addLast("uwap2codec", new ProtocolCodecFilter(new S2SEncoder(), new S2SDecoder()));
		this.connector.getFilterChain().addLast("uwap2databean", new DataBeanFilter());
	}

	public AbstractData handle(AbstractData message) throws Exception {
		return null;
	}

	@Override
	protected void connected() {
		// TODO Auto-generated method stub
		
	}
}