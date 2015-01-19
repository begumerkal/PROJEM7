package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class OtherNearAttack extends AbstractData {
	private int     battleId;	//战斗id
	private int     playerOrGuai;	//0:player 1:guai
	private int     currentId;	//角色id
	private int     leftRight;	//1：左 0：右（向左还是向右）

	public OtherNearAttack(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OtherNearAttack, sessionId, serial);
	}

	public OtherNearAttack() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OtherNearAttack);
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

	public int getLeftRight() {
		return leftRight;
	}

	public void setLeftRight(int leftRight) {
		this.leftRight = leftRight;
	}

}
