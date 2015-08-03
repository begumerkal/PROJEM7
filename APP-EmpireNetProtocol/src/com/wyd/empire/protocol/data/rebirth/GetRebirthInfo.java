package com.wyd.empire.protocol.data.rebirth;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetRebirthInfo extends AbstractData {

	public GetRebirthInfo(int sessionId, int serial) {
		super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_GetRebirthInfo, sessionId, serial);
	}

	public GetRebirthInfo() {
		super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_GetRebirthInfo);
	}
}
