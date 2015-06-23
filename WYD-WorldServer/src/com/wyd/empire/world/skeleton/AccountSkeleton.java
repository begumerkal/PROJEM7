package com.wyd.empire.world.skeleton;

import java.net.InetSocketAddress;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.wyd.empire.protocol.data.server.Heartbeat;
import com.wyd.empire.protocol.data.server.WorldServerToAccountServer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
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
		this.config.setIdleTime(IdleStatus.BOTH_IDLE,120);
		this.connector.getFilterChain().addLast("uwap2codec", new ProtocolCodecFilter(new S2SEncoder(), new S2SDecoder()));
		this.connector.getFilterChain().addLast("uwap2databean", new DataBeanFilter());
	}
	@Override
	public AbstractData handle(AbstractData message) throws Exception {
		System.out.println("handle not found:"+message);
		return null;
	}
	@Override
	protected void connected() {
		String area = ServiceManager.getManager().getConfiguration().getString("area");
		String machinecode = ServiceManager.getManager().getConfiguration().getString("machinecode");
		WorldServerToAccountServer wta = new WorldServerToAccountServer();
		wta.setWorldServerId(area+"_"+machinecode);
		send(wta);
	}
	@Override
	protected void idle() {
		Heartbeat heart = new Heartbeat();
		send(heart);
	}
}