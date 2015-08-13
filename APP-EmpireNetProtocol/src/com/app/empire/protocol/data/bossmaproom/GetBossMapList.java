package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetBossMapList extends AbstractData {
	
	public GetBossMapList(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetBossMapList, sessionId, serial);
	}

	public GetBossMapList() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetBossMapList);
	}
	

}
