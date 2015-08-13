package com.app.empire.protocol.data.rebirth;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetRebirthInfo extends AbstractData {

	public GetRebirthInfo(int sessionId, int serial) {
		super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_GetRebirthInfo, sessionId, serial);
	}

	public GetRebirthInfo() {
		super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_GetRebirthInfo);
	}
}
