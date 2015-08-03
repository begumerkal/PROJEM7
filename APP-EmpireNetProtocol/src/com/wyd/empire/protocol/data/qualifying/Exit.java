package com.wyd.empire.protocol.data.qualifying;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Exit extends AbstractData {
	
	public Exit(int sessionId, int serial) {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_Exit, sessionId, serial);
	}

	public Exit() {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_Exit);
	}

}
