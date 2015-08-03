package com.wyd.empire.protocol.data.bossmaproom;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetRoomList extends AbstractData {

	public GetRoomList(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetRoomList, sessionId, serial);
	}

	public GetRoomList() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetRoomList);
	}
}
