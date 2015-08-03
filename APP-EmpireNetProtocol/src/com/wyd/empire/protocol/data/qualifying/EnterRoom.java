package com.wyd.empire.protocol.data.qualifying;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class EnterRoom extends AbstractData {
	
	public EnterRoom(int sessionId, int serial) {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_EnterRoom, sessionId, serial);
	}

	public EnterRoom() {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_EnterRoom);
	}

}
