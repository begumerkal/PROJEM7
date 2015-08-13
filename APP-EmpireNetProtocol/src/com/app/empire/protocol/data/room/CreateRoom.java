package com.app.empire.protocol.data.room;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CreateRoom extends AbstractData {
	private String  roomName;
	private int     battleMode;
	private int     playerNumMode;
	private String  passWord;
	private int     startMode;
	private int     serviceMode;
	private boolean eventMode;
	
	public CreateRoom(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_CreateRoom, sessionId, serial);
	}

	public CreateRoom() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_CreateRoom);
	}
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getBattleMode() {
		return battleMode;
	}

	public void setBattleMode(int battleMode) {
		this.battleMode = battleMode;
	}

	public int getPlayerNumMode() {
		return playerNumMode;
	}

	public void setPlayerNumMode(int playerNumMode) {
		this.playerNumMode = playerNumMode;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getStartMode() {
		return startMode;
	}

	public void setStartMode(int startMode) {
		this.startMode = startMode;
	}

    public int getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(int serviceMode) {
        this.serviceMode = serviceMode;
    }

	public boolean getEventMode() {
		return eventMode;
	}

	public void setEventMode(boolean eventMode) {
		this.eventMode = eventMode;
	}
}
