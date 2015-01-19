package com.wyd.empire.protocol.data.qualifying;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Detail extends AbstractData {
	
	public Detail(int sessionId, int serial) {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_Detail, sessionId, serial);
	}

	public Detail() {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_Detail);
	}

}
