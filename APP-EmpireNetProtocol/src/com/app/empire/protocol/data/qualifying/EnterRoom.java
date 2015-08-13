package com.app.empire.protocol.data.qualifying;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class EnterRoom extends AbstractData {
	
	public EnterRoom(int sessionId, int serial) {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_EnterRoom, sessionId, serial);
	}

	public EnterRoom() {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_EnterRoom);
	}

}
