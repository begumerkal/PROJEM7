package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Invite extends AbstractData {
	
	private int battleTeamId;//	战队ID（相当于战斗时的房间ID）
	private int playerId;//		玩家Id

	
    public Invite(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_Invite, sessionId, serial);
    }

    public Invite() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_Invite);
    }

	public int getBattleTeamId() {
		return battleTeamId;
	}

	public void setBattleTeamId(int battleTeamId) {
		this.battleTeamId = battleTeamId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
}
