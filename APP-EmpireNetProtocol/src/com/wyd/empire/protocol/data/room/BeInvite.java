package com.wyd.empire.protocol.data.room;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class BeInvite extends AbstractData {
	private int roomId;
	private int battleMode;
	private String playerName;

	public BeInvite(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_BeInvite, sessionId, serial);
	}

	public BeInvite() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_BeInvite);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getBattleMode() {
		return battleMode;
	}

	public void setBattleMode(int battleMode) {
		this.battleMode = battleMode;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
