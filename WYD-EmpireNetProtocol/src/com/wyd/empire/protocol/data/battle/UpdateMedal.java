package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class UpdateMedal extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     campCount;
	private int[]   campId;
	private int[]   campMedalNum;

	public UpdateMedal(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_UpdateMedal, sessionId, serial);
	}

	public UpdateMedal() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_UpdateMedal);
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

	public int getCampCount() {
		return campCount;
	}

	public void setCampCount(int campCount) {
		this.campCount = campCount;
	}

	public int[] getCampId() {
		return campId;
	}

	public void setCampId(int[] campId) {
		this.campId = campId;
	}

	public int[] getCampMedalNum() {
		return campMedalNum;
	}

	public void setCampMedalNum(int[] campMedalNum) {
		this.campMedalNum = campMedalNum;
	}
}
