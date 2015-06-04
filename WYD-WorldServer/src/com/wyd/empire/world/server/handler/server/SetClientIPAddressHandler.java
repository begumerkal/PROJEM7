package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.server.SetClientIPAddress;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 设置客户端IP
 * 
 * @author doter
 *
 */
public class SetClientIPAddressHandler implements IDataHandler {
	Logger log;

	public SetClientIPAddressHandler() {
		this.log = Logger.getLogger(SetClientIPAddressHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
		SetClientIPAddress address = (SetClientIPAddress) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		Client client = session.getAndCreateClient(address.getSession());
		client.setIp(address.getIp());
		return null;
	}
}