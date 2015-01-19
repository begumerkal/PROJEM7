package com.wyd.empire.protocol.data.bossmaproom;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SelectRoomOk extends AbstractData {
	private int roomId;
	private String passWord;
	
	public SelectRoomOk(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SelectRoomOk, sessionId, serial);
	}

	public SelectRoomOk() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SelectRoomOk);
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
