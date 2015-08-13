package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class EnterRoom extends AbstractData {
	
	private int roomId;
	
	public EnterRoom(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_EnterRoom, sessionId, serial);
	}

	public EnterRoom() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_EnterRoom);
	}
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
