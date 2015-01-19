package com.wyd.empire.protocol.data.server;

import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;

public class SetClientIPAddress extends AbstractData {
	private int session;
	private String ip;

	public SetClientIPAddress(int sessionId, int serial) {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_SetClientIPAddress, sessionId, serial);
	}

	public SetClientIPAddress() {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_SetClientIPAddress);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}

}
