package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UpdateSeat extends AbstractData {
	private int     battleTeamId;
	private int     oldSeat;
	private int     newSeat;
	public UpdateSeat(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_UpdateSeat, sessionId, serial);
	}

	public UpdateSeat() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_UpdateSeat);
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

	public int getNewSeat() {
		return newSeat;
	}

	public void setNewSeat(int newSeat) {
		this.newSeat = newSeat;
	}
}
