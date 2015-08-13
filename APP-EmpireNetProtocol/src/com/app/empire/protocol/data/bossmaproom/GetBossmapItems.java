package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetBossmapItems extends AbstractData {

	public GetBossmapItems(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetBossmapItems, sessionId, serial);
	}

	public GetBossmapItems() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetBossmapItems);
	}
}
