package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetRoomList extends AbstractData {

	public GetRoomList(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetRoomList, sessionId, serial);
	}

	public GetRoomList() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetRoomList);
	}
}
