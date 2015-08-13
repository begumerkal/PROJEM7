package com.app.empire.protocol.data.room;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetRoomListOk extends AbstractData {
	private int      roomCount;
	private int[]    roomId;
	private String[] roomName;
	private int[]    battleStatus;
	private int[]    battleMode;
	private int[]    playerNumMode;
	private String[] passWord;
	private int[]    playerNum;
	private int[]    startMode;
	private boolean[] roomStaus;
	
	public GetRoomListOk(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_GetRoomListOk, sessionId, serial);
	}

	public GetRoomListOk() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_GetRoomListOk);
	}
	
	public int getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(int roomCount) {
		this.roomCount = roomCount;
	}

	public int[] getRoomId() {
		return roomId;
	}

	public void setRoomId(int[] roomId) {
		this.roomId = roomId;
	}

	public String[] getRoomName() {
		return roomName;
	}

	public void setRoomName(String[] roomName) {
		this.roomName = roomName;
	}

	public int[] getBattleStatus() {
		return battleStatus;
	}

	public void setBattleStatus(int[] battleStatus) {
		this.battleStatus = battleStatus;
	}

	public int[] getBattleMode() {
		return battleMode;
	}

	public void setBattleMode(int[] battleMode) {
		this.battleMode = battleMode;
	}

	public int[] getPlayerNumMode() {
		return playerNumMode;
	}

	public void setPlayerNumMode(int[] playerNumMode) {
		this.playerNumMode = playerNumMode;
	}

	public String[] getPassWord() {
		return passWord;
	}

	public void setPassWord(String[] passWord) {
		this.passWord = passWord;
	}

	public int[] getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int[] playerNum) {
		this.playerNum = playerNum;
	}

	public int[] getStartMode() {
		return startMode;
	}

	public void setStartMode(int[] startMode) {
		this.startMode = startMode;
	}

	public boolean[] getRoomStaus() {
		return roomStaus;
	}

	public void setRoomStaus(boolean[] roomStaus) {
		this.roomStaus = roomStaus;
	}
}
