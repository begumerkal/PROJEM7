package com.wyd.empire.protocol.data.map;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetMapList extends AbstractData {

	public GetMapList(int sessionId, int serial) {
		super(Protocol.MAIN_MAP, Protocol.MAP_GetMapList, sessionId, serial);
	}

	public GetMapList() {
		super(Protocol.MAIN_MAP, Protocol.MAP_GetMapList);
	}
}
