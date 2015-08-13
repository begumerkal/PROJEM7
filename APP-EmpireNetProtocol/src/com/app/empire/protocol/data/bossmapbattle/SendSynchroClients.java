package com.app.empire.protocol.data.bossmapbattle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SendSynchroClients extends AbstractData {
	private int battleId;     // 战斗id
	private int playerOrGuai; // 0:player 1:guai
	private int currentId;    // 角色id
	private int state;        // 状态
	private int[] parameter;  // 参数数组

	public SendSynchroClients(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_RequestSynchroClients, sessionId, serial);
	}

	public SendSynchroClients() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_RequestSynchroClients);
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int[] getParameter() {
		return parameter;
	}

	public void setParameter(int[] parameter) {
		this.parameter = parameter;
	}
	
}
