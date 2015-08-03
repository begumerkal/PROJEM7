package com.wyd.empire.protocol.data.room;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class QuickGame extends AbstractData {
	private int     battleMode;
	private int     playerNumMode;
	private int     startMode;
	public QuickGame(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_QuickGame, sessionId, serial);
	}

	public QuickGame() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_QuickGame);
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

	public int getStartMode() {
		return startMode;
	}

	public void setStartMode(int startMode) {
		this.startMode = startMode;
	}

	
}
