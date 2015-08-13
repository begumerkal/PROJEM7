package com.app.empire.protocol.data.bossmapbattle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class BuildGuai extends AbstractData {
	private int     battleId;	//战斗id
	private int     playerOrGuai;	//0:player 1:guai
	private int     currentId;	//角色id,谁生成的
	private int     guaiCount;	//生成的数量
	private int[]   guaiBattleId;	//生成怪的战斗id
	private int[]   guaiId;	//怪的数据形象id
	private int[]   guaiPositionX;	//X坐标
	private int[]   guaiPositionY;	//Y坐标

	public BuildGuai(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_BuildGuai, sessionId, serial);
	}

	public BuildGuai() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_BuildGuai);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int getPlayerOrGuai() {
		return playerOrGuai;
	}

	public void setPlayerOrGuai(int playerOrGuai) {
		this.playerOrGuai = playerOrGuai;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	public int getGuaiCount() {
		return guaiCount;
	}

	public void setGuaiCount(int guaiCount) {
		this.guaiCount = guaiCount;
	}

	public int[] getGuaiBattleId() {
		return guaiBattleId;
	}

	public void setGuaiBattleId(int[] guaiBattleId) {
		this.guaiBattleId = guaiBattleId;
	}

	public int[] getGuaiId() {
		return guaiId;
	}

	public void setGuaiId(int[] guaiId) {
		this.guaiId = guaiId;
	}

	public int[] getGuaiPositionX() {
		return guaiPositionX;
	}

	public void setGuaiPositionX(int[] guaiPositionX) {
		this.guaiPositionX = guaiPositionX;
	}

	public int[] getGuaiPositionY() {
		return guaiPositionY;
	}

	public void setGuaiPositionY(int[] guaiPositionY) {
		this.guaiPositionY = guaiPositionY;
	}

}
