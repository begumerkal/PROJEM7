package com.wyd.empire.protocol.data.qualifying;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class EnterRoomOk extends AbstractData {
	
	private	int[]	playerId;	//玩家id
	private	int[]	rankings;	//排名
	private	String[]	names;	//名称
	private	int[]	scores;	//积分
	private	int[]	ranks;	//军衔等级
	private	int[]	successRates;	//胜率
	private String[]    rewardIcons;  //排位赛奖励图标
	private String[]	rewardName; //奖励名称
	private int[]  rewardValidTime;	//奖励有效期
	private	int	remainingTime;	//本赛季剩余时间
	private	int	myRanking;	//我的排名
	private	int	myScore;	//我的积分
	private	int	myRank;	//我的军衔等级
	private	String	myRankName;	//我的军衔名称
	private	int	myExp;	//我的荣誉值
	private	int	myNextExp;	//我的下次升级需要荣誉值
	private	boolean	hasOpened;	//排位赛是否开启

	
	public EnterRoomOk(int sessionId, int serial) {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_EnterRoomOk, sessionId, serial);
	}

	public EnterRoomOk() {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_EnterRoomOk);
	}

	public int[] getRankings() {
		return rankings;
	}

	public void setRankings(int[] rankings) {
		this.rankings = rankings;
	}

	public int[] getScores() {
		return scores;
	}

	public void setScores(int[] scores) {
		this.scores = scores;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public int[] getRanks() {
		return ranks;
	}

	public void setRanks(int[] ranks) {
		this.ranks = ranks;
	}

	public int[] getSuccessRates() {
		return successRates;
	}

	public void setSuccessRates(int[] successRates) {
		this.successRates = successRates;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int getMyRanking() {
		return myRanking;
	}

	public void setMyRanking(int myRanking) {
		this.myRanking = myRanking;
	}

	public int getMyScore() {
		return myScore;
	}

	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}

	public int getMyRank() {
		return myRank;
	}

	public void setMyRank(int myRank) {
		this.myRank = myRank;
	}

	public String getMyRankName() {
		return myRankName;
	}

	public void setMyRankName(String myRankName) {
		this.myRankName = myRankName;
	}

	public int getMyExp() {
		return myExp;
	}

	public void setMyExp(int myExp) {
		this.myExp = myExp;
	}

	public int getMyNextExp() {
		return myNextExp;
	}

	public void setMyNextExp(int myNextExp) {
		this.myNextExp = myNextExp;
	}

	public boolean isHasOpened() {
		return hasOpened;
	}

	public void setHasOpened(boolean hasOpened) {
		this.hasOpened = hasOpened;
	}

	public int[] getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int[] playerId) {
		this.playerId = playerId;
	}

	public String[] getRewardIcons() {
		return rewardIcons;
	}

	public void setRewardIcons(String[] rewardIcons) {
		this.rewardIcons = rewardIcons;
	}

	public String[] getRewardName() {
		return rewardName;
	}

	public void setRewardName(String[] rewardName) {
		this.rewardName = rewardName;
	}

	public int[] getRewardValidTime() {
		return rewardValidTime;
	}

	public void setRewardValidTime(int[] rewardValidTime) {
		this.rewardValidTime = rewardValidTime;
	}

}
