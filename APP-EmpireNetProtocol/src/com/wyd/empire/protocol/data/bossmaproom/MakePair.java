package com.wyd.empire.protocol.data.bossmaproom;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class MakePair extends AbstractData {
	private int roomId;

	public MakePair(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_MakePair, sessionId, serial);
	}

	public MakePair() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_MakePair);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
