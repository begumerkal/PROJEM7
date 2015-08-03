package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class QuitRoom extends AbstractData {
	private int battleTeamId;
	private int oldSeat;

	public QuitRoom(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitRoom, sessionId, serial);
	}

	public QuitRoom() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_QuitRoom);
	}


	public int getBattleTeamId() {
		return battleTeamId;
	}

	public void setBattleTeamId(int battleTeamId) {
		this.battleTeamId = battleTeamId;
	}

	public int getOldSeat() {
		return oldSeat;
	}

	public void setOldSeat(int oldSeat) {
		this.oldSeat = oldSeat;
	}
}
