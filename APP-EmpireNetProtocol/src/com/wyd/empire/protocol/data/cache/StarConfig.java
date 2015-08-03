package com.wyd.empire.protocol.data.cache;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class StarConfig  extends AbstractData {
	private int[] starLevel; // 星级（1-10）
	private int[] starExp;   // 每一级需要的经验
	private int   goldRate;  // 升星消耗金币系数（升星需要消耗的金币=升星石数量*系数*(当前星级+1）)
	private int[] attackRate;// 每一级增加攻击系数
	private int[] defendRate;// 每一级增加防御系数
	private int[] hpRate;    // 每一级增加血量系数
	
	public StarConfig(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_StarConfig, sessionId, serial);
	}

	public StarConfig() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_StarConfig);
	}

	public int[] getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(int[] starLevel) {
		this.starLevel = starLevel;
	}

	public int[] getStarExp() {
		return starExp;
	}

	public void setStarExp(int[] starExp) {
		this.starExp = starExp;
	}

	public int getGoldRate() {
		return goldRate;
	}

	public void setGoldRate(int goldRate) {
		this.goldRate = goldRate;
	}

	public int[] getAttackRate() {
		return attackRate;
	}

	public void setAttackRate(int[] attackRate) {
		this.attackRate = attackRate;
	}

	public int[] getDefendRate() {
		return defendRate;
	}

	public void setDefendRate(int[] defendRate) {
		this.defendRate = defendRate;
	}

	public int[] getHpRate() {
		return hpRate;
	}

	public void setHpRate(int[] hpRate) {
		this.hpRate = hpRate;
	}
}
