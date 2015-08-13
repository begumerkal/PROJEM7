package com.app.empire.protocol.data.qualifying;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class Detail extends AbstractData {
	
	public Detail(int sessionId, int serial) {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_Detail, sessionId, serial);
	}

	public Detail() {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_Detail);
	}

}
