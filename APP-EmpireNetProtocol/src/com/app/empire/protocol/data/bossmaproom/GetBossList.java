package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetBossList extends AbstractData {

	public GetBossList(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetBossList, sessionId, serial);
	}

	public GetBossList() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetBossList);
	}
}
