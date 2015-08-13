package com.app.empire.protocol.data.qualifying;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class Exit extends AbstractData {
	
	public Exit(int sessionId, int serial) {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_Exit, sessionId, serial);
	}

	public Exit() {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_Exit);
	}

}
