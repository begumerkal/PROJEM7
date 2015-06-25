package com.wyd.empire.protocol.data.server;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Heartbeat extends AbstractData {

	public Heartbeat(int sessionId, int serial) {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_Heartbeat, sessionId, serial);
	}

	public Heartbeat() {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_Heartbeat);
	}

}
