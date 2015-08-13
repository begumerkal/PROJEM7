package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SkillEquip extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     itemcount;
	private int[]   itmeIds;
	public SkillEquip(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_SkillEquip, sessionId, serial);
	}

	public SkillEquip() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_SkillEquip);
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

	public int getItemcount() {
		return itemcount;
	}

	public void setItemcount(int itemcount) {
		this.itemcount = itemcount;
	}

	public int[] getItmeIds() {
		return itmeIds;
	}

	public void setItmeIds(int[] itmeIds) {
		this.itmeIds = itmeIds;
	}
}
