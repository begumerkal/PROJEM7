package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class PositionsInMap extends AbstractData {
	private int     battleId;
	private int     postionCount;
	private int[]   x;
	private int[]   y;
	public PositionsInMap(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_PositionsInMap, sessionId, serial);
	}

	public PositionsInMap() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_PositionsInMap);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int getPostionCount() {
		return postionCount;
	}

	public void setPostionCount(int postionCount) {
		this.postionCount = postionCount;
	}

	public int[] getX() {
		return x;
	}

	public void setX(int[] x) {
		this.x = x;
	}

	public int[] getY() {
		return y;
	}

	public void setY(int[] y) {
		this.y = y;
	}
}
