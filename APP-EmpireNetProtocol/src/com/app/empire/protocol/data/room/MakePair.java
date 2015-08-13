package com.app.empire.protocol.data.room;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class MakePair extends AbstractData {
	private int roomId;
	private int serviceMode;//服务器模式（0本服对战，1跨服对战）
	public MakePair(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_MakePair, sessionId, serial);
	}

	public MakePair() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_MakePair);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

    public int getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(int serviceMode) {
        this.serviceMode = serviceMode;
    }
    
}
