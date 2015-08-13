package com.app.empire.protocol.data.bossmapbattle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class RequestGuaiBattleIdOk extends AbstractData {
	private int     battleId;	//战斗id
	private int     count;	//请求的数量
	private int[]   guaiBattleId;		//生成怪的战斗id,用于生成怪的时使,使各个客户端id不冲突


	public RequestGuaiBattleIdOk(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_RequestGuaiBattleIdOk, sessionId, serial);
	}

	public RequestGuaiBattleIdOk() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_RequestGuaiBattleIdOk);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int[] getGuaiBattleId() {
		return guaiBattleId;
	}

	public void setGuaiBattleId(int[] guaiBattleId) {
		this.guaiBattleId = guaiBattleId;
	}
}
