package com.app.empire.protocol.data.room;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetRoomList extends AbstractData {

	public GetRoomList(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_GetRoomList, sessionId, serial);
	}

	public GetRoomList() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_GetRoomList);
	}
}
