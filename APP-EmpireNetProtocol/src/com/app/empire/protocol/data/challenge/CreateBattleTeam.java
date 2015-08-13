package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CreateBattleTeam extends AbstractData {
	
	private String teamName;
	
    public CreateBattleTeam(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_CreateBattleTeam, sessionId, serial);
    }

    public CreateBattleTeam() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_CreateBattleTeam);
    }

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
    
    
}
