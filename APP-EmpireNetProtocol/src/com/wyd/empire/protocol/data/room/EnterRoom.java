package com.wyd.empire.protocol.data.room;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class EnterRoom extends AbstractData {
	
	private int roomId;
	
	public EnterRoom(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_EnterRoom, sessionId, serial);
	}

	public EnterRoom() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_EnterRoom);
	}
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
