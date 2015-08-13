package com.app.empire.protocol.data.bossmapbattle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class OutOfScene extends AbstractData {
	private int     battleId;
	private int     playerOrGuai1;
	private int     playerId;
	private int     playerOrGuai2;
	private int     currentId;

	public OutOfScene(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OutOfScene, sessionId, serial);
	}

	public OutOfScene() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OutOfScene);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	public int getPlayerOrGuai1() {
		return playerOrGuai1;
	}

	public void setPlayerOrGuai1(int playerOrGuai1) {
		this.playerOrGuai1 = playerOrGuai1;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getPlayerOrGuai2() {
		return playerOrGuai2;
	}

	public void setPlayerOrGuai2(int playerOrGuai2) {
		this.playerOrGuai2 = playerOrGuai2;
	}
}
