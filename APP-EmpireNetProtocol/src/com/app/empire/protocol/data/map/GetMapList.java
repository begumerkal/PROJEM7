package com.app.empire.protocol.data.map;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetMapList extends AbstractData {

	public GetMapList(int sessionId, int serial) {
		super(Protocol.MAIN_MAP, Protocol.MAP_GetMapList, sessionId, serial);
	}

	public GetMapList() {
		super(Protocol.MAIN_MAP, Protocol.MAP_GetMapList);
	}
}
