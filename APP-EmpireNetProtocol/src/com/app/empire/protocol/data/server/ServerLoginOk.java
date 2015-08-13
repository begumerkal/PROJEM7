package com.app.empire.protocol.data.server;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class ServerLoginOk extends AbstractData {
 
	public ServerLoginOk(int sessionId, int serial) {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_ServerLoginOk, sessionId, serial);
	}

	public ServerLoginOk() {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_ServerLoginOk);
	}
 
}
