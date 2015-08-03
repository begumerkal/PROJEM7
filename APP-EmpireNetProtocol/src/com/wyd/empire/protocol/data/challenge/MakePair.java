package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class MakePair extends AbstractData {
	private int battleTeamId;
	private int serviceMode;
	
	public MakePair(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_MakePair, sessionId, serial);
	}

	public MakePair() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_MakePair);
	}

	public int getBattleTeamId() {
		return battleTeamId;
	}

	public void setBattleTeamId(int battleTeamId) {
		this.battleTeamId = battleTeamId;
	}

	public int getServiceMode() {
		return serviceMode;
	}

	public void setServiceMode(int serviceMode) {
		this.serviceMode = serviceMode;
	}

}
