package com.wyd.empire.protocol.data.singlemap;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ChallengeSuccessOk extends AbstractData {
	private int addExp;//增加的经验
	private int exp;//当前的经验(没有增加经验前的)
	private int upgradeExp;//当前升级所需经验
	private int nextUpgradeExp;//下一级升级所需经验
	private int[] getReward;//获得到的奖励
	private String[] rewardIcon;//奖励图标（全部）
	private String[] rewardName;//奖励名称（全部）
	private int guildAddExp; //公会技能加的经验
	
	
	public ChallengeSuccessOk(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_ChallengeSuccessOk, sessionId, serial);
    }
	public ChallengeSuccessOk(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_ChallengeSuccessOk);
	}
	public int getAddExp() {
		return addExp;
	}
	public void setAddExp(int addExp) {
		this.addExp = addExp;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getUpgradeExp() {
		return upgradeExp;
	}
	public void setUpgradeExp(int upgradeExp) {
		this.upgradeExp = upgradeExp;
	}
	public int getNextUpgradeExp() {
		return nextUpgradeExp;
	}
	public void setNextUpgradeExp(int nextUpgradeExp) {
		this.nextUpgradeExp = nextUpgradeExp;
	}
	public int[] getGetReward() {
		return getReward;
	}
	public void setGetReward(int[] getReward) {
		this.getReward = getReward;
	}
	public String[] getRewardIcon() {
		return rewardIcon;
	}
	public void setRewardIcon(String[] rewardIcon) {
		this.rewardIcon = rewardIcon;
	}
	public String[] getRewardName() {
		return rewardName;
	}
	public void setRewardName(String[] rewardName) {
		this.rewardName = rewardName;
	}
	
	public int getGuildAddExp() {
		return guildAddExp;
	}
	public void setGuildAddExp(int guildAddExp) {
		this.guildAddExp = guildAddExp;
	}
	

}
