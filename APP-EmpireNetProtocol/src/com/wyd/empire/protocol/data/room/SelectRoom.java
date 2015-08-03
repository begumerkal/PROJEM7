package com.wyd.empire.protocol.data.room;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SelectRoom extends AbstractData {
	private int roomId;
	private String passWord;
	public SelectRoom(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_SelectRoom, sessionId, serial);
	}

	public SelectRoom() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_SelectRoom);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}
