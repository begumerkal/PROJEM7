package com.app.empire.protocol.data.bossmapbattle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SendPlayerBattleAttribute extends AbstractData {
	private 	int	battleId;	//战斗id
	private 	int	playerId;	//角色
	private 	int	hp;	//血量
	private 	int	pf;	//体力
	private 	int	angry;	//怒气
	private 	int	hpMax;  //最大生命
	private 	int	pfMax;	//最大体力
	private 	int	angryMax;	//最大怒气
	private 	int	attack;	//攻击力
	private 	int	defend;	//防御力
	private 	int bigSkillAttack; //大招攻击力


	public SendPlayerBattleAttribute(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_SendPlayerBattleAttribute, sessionId, serial);
	}

	public SendPlayerBattleAttribute() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_SendPlayerBattleAttribute);
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

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}


	public int getAngry() {
		return angry;
	}

	public void setAngry(int angry) {
		this.angry = angry;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefend() {
		return defend;
	}

	public void setDefend(int defend) {
		this.defend = defend;
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}


	public int getAngryMax() {
		return angryMax;
	}

	public void setAngryMax(int angryMax) {
		this.angryMax = angryMax;
	}

	public int getPf() {
		return pf;
	}

	public void setPf(int pf) {
		this.pf = pf;
	}

	public int getPfMax() {
		return pfMax;
	}

	public void setPfMax(int pfMax) {
		this.pfMax = pfMax;
	}

	public int getBigSkillAttack() {
		return bigSkillAttack;
	}

	public void setBigSkillAttack(int bigSkillAttack) {
		this.bigSkillAttack = bigSkillAttack;
	}


}
