package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CreateRoomOk extends AbstractData {
	
	private int roomId;
	
	public CreateRoomOk(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_CreateRoomOk, sessionId, serial);
	}

	public CreateRoomOk() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_CreateRoomOk);
	}
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
