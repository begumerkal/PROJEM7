package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class OtherSkillEquip extends AbstractData {
	private int     battleId;
	private int     playerId;
	private int     currentPlayerId;
	private int     itemcount;
	private int[]   itmeIds;
	public OtherSkillEquip(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherSkillEquip, sessionId, serial);
	}

	public OtherSkillEquip() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherSkillEquip);
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

	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void setCurrentPlayerId(int currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
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
