package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class KillGuai extends AbstractData {
	private int     battleId;
	private int[]   guaiBattleIds;
	
	public KillGuai(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_KillGuai, sessionId, serial);
	}

	public KillGuai() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_KillGuai);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int[] getGuaiBattleIds() {
		return guaiBattleIds;
	}

	public void setGuaiBattleIds(int[] guaiBattleIds) {
		this.guaiBattleIds = guaiBattleIds;
	}

}
