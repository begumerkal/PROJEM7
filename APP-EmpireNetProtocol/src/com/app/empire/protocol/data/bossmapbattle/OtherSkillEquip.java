package com.app.empire.protocol.data.bossmapbattle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class OtherSkillEquip extends AbstractData {
	private int     battleId;
	private int     playerOrGuai;
	private int     currentId;
	private int     itemcount;
	private int[]   itmeIds;

	public OtherSkillEquip(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OtherSkillEquip, sessionId, serial);
	}

	public OtherSkillEquip() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_OtherSkillEquip);
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
