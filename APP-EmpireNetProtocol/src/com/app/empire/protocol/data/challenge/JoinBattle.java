package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class JoinBattle extends AbstractData {
	
	private	int	playerId;	//玩家ID
	
    public JoinBattle(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_JoinBattle, sessionId, serial);
    }

    public JoinBattle() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_JoinBattle);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}


}
