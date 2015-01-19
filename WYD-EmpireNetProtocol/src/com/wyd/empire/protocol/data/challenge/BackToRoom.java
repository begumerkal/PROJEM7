package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class BackToRoom extends AbstractData {
	private int     battleTeamId;
	public BackToRoom(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_BackToRoom, sessionId, serial);
	}

	public BackToRoom() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_BackToRoom);
	}

	public int getBattleTeamId() {
		return battleTeamId;
	}

	public void setBattleTeamId(int battleTeamId) {
		this.battleTeamId = battleTeamId;
	}

}
