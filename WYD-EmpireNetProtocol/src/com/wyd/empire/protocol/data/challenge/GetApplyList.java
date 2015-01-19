package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetApplyList extends AbstractData {
	
	private int playerId;//	玩家ID
	private int battleTeamId;//	战队ID（相当于战斗时的房间ID）

	
    public GetApplyList(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetApplyList, sessionId, serial);
    }

    public GetApplyList() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetApplyList);
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
