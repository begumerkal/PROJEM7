package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class AuditPlayer extends AbstractData {
	
	private int playerId;
	private int battleTeamId;
	private int status;
	
    public AuditPlayer(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_AuditPlayer, sessionId, serial);
    }

    public AuditPlayer() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_AuditPlayer);
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
}
