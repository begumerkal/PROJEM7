package com.wyd.empire.world.skeleton;

import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.data.server.ServerLogin;
import com.wyd.net.Connector;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.data.DataBeanFilter;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.protocol.s2s.S2SDecoder;
import com.wyd.protocol.s2s.S2SEncoder;

public class AccountSkeleton extends Connector implements IDataHandler {

	public AccountSkeleton(String id, InetSocketAddress address) {
		super(id, address, true);
	}

	@Override
	public void init() {
		this.connector.getFilterChain().addLast("uwap2codec", new ProtocolCodecFilter(new S2SEncoder(), new S2SDecoder()));
		this.connector.getFilterChain().addLast("uwap2databean", new DataBeanFilter());
	}

	@Override
	protected void sendSecureAuthMessage() {
		ServerLogin login = new ServerLogin();
		login.setId(getUserName());
		login.setPassword(getPassword());
		send0(login);
	}

	@Override
	public AbstractData processLogin(AbstractData data) throws Exception {
		byte type = data.getType();
		byte subType = data.getSubType();
		if (type == Protocol.MAIN_SERVER) {
			if (subType == Protocol.SERVER_ServerLoginOk) {
				loginOk();
			}
		} else if (subType == Protocol.SERVER_ServerLoginFailed) {
			loginFailed();
		}
		return null;
	}

	public AbstractData handle(AbstractData message) throws Exception {
		return null;
	}
}