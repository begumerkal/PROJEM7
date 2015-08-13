package com.app.empire.protocol.data.room;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SoundRoom extends AbstractData {
	private int severId;	//服务器ID
	private int roomId;		//房间ID
	private int location;	//发送位置（0创建房间，1加入房间，2退出房间）
	private int mark;		//语音房间标示（0普通房间，1副本房价）
	
	public SoundRoom(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_SoundRoom, sessionId, serial);
	}

	public SoundRoom() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_SoundRoom);
	}
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getSeverId() {
		return severId;
	}

	public void setSeverId(int severId) {
		this.severId = severId;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
}
