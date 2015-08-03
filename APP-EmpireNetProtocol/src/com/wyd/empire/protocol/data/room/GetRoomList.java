package com.wyd.empire.protocol.data.room;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetRoomList extends AbstractData {

	public GetRoomList(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_GetRoomList, sessionId, serial);
	}

	public GetRoomList() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_GetRoomList);
	}
}
