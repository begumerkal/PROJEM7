package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class PlayerEnter extends AbstractData {
	private int     playerId;
	private String  playerName;
	private int     playerLevel;
	private int     playerSex;
	private int     seatIndex;
	private String[]  playerEquipment;

	public PlayerEnter(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_PlayerEnter, sessionId, serial);
	}

	public PlayerEnter() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_PlayerEnter);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}
	
	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

	public int getPlayerSex() {
		return playerSex;
	}

	public void setPlayerSex(int playerSex) {
		this.playerSex = playerSex;
	}

	public int getSeatIndex() {
		return seatIndex;
	}

	public void setSeatIndex(int seatIndex) {
		this.seatIndex = seatIndex;
	}

	public String[] getPlayerEquipment() {
		return playerEquipment;
	}

	public void setPlayerEquipment(String[] playerEquipment) {
		this.playerEquipment = playerEquipment;
	}
}
