package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class PlayerReborn extends AbstractData {
	private int     battleId;
	private int     playercount;
	private int[]   playerIds;
	private int[]   postionX;
	private int[]   postionY;
	private int     guaicount;	//	怪数
	private int[]   guaiBattleIds;	//	所有人id
	private int[]   guaipostionX;	//	x坐标
	private int[]   guaipostionY;	//	y坐标


	public PlayerReborn(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_PlayerReborn, sessionId, serial);
	}

	public PlayerReborn() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_PlayerReborn);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int getGuaicount() {
		return guaicount;
	}

	public void setGuaicount(int guaicount) {
		this.guaicount = guaicount;
	}

	public int[] getGuaiBattleIds() {
		return guaiBattleIds;
	}

	public void setGuaiBattleIds(int[] guaiBattleIds) {
		this.guaiBattleIds = guaiBattleIds;
	}

	public int[] getGuaipostionX() {
		return guaipostionX;
	}

	public void setGuaipostionX(int[] guaipostionX) {
		this.guaipostionX = guaipostionX;
	}

	public int[] getGuaipostionY() {
		return guaipostionY;
	}

	public void setGuaipostionY(int[] guaipostionY) {
		this.guaipostionY = guaipostionY;
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
