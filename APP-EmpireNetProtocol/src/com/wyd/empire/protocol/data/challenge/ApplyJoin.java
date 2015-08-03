package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ApplyJoin extends AbstractData {
	
	private int playerId;
	private int battleTeamId;
	
    public ApplyJoin(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_ApplyJoin, sessionId, serial);
    }

    public ApplyJoin() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_ApplyJoin);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getBattleTeamId() {
		return battleTeamId;
	}

	public void setBattleTeamId(int battleTeamId) {
		this.battleTeamId = battleTeamId;
	}
    
}
