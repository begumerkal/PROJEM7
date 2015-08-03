package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SkillEquip extends AbstractData {
	private int     battleId;
	private int     playerOrGuai;
	private int     currentId;
	private int     itemcount;
	private int[]   itmeIds;
	public SkillEquip(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_SkillEquip, sessionId, serial);
	}

	public SkillEquip() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_SkillEquip);
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
