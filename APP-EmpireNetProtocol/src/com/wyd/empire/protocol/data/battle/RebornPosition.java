package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class RebornPosition extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     playercount;
	private int[]   playerIds;
	private int[]   postionX;
	private int[]   postionY;

	public RebornPosition(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_RebornPosition, sessionId, serial);
	}

	public RebornPosition() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_RebornPosition);
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
