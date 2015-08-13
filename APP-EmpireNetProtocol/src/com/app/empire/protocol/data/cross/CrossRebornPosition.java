package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class CrossRebornPosition extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     playercount;
	private int[]   playerIds;
	private int[]   postionX;
	private int[]   postionY;

	public CrossRebornPosition() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossRebornPosition);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getPlayercount() {
		return playercount;
	}

	public void setPlayercount(int playercount) {
		this.playercount = playercount;
	}
	
	public int[] getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(int[] playerIds) {
		this.playerIds = playerIds;
	}

	public int[] getPostionX() {
		return postionX;
	}

	public void setPostionX(int[] postionX) {
		this.postionX = postionX;
	}

	public int[] getPostionY() {
		return postionY;
	}

	public void setPostionY(int[] postionY) {
		this.postionY = postionY;
	}
}
