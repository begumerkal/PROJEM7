package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetBattleTeamList extends AbstractData {
	
	private	int[]	battleTeamId;//战队ID（相当于战斗时的房间ID）
	private	int[]	hasNum;//战队已有人数
	private	int[]	totalNum;//战队总人数
	private	String[]	teamName;//	战队名称
	private	String[]	teamLeader;//	队长名称
	private	int[]	avgIntegral;//	战队平均积分

	
    public GetBattleTeamList(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetBattleTeamList, sessionId, serial);
    }

    public GetBattleTeamList() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetBattleTeamList);
    }

	public int[] getBattleTeamId() {
		return battleTeamId;
	}

	public void setBattleTeamId(int[] battleTeamId) {
		this.battleTeamId = battleTeamId;
	}

	public int[] getHasNum() {
		return hasNum;
	}

	public void setHasNum(int[] hasNum) {
		this.hasNum = hasNum;
	}

	public int[] getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int[] totalNum) {
		this.totalNum = totalNum;
	}

	public String[] getTeamName() {
		return teamName;
	}

	public void setTeamName(String[] teamName) {
		this.teamName = teamName;
	}

	public String[] getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String[] teamLeader) {
		this.teamLeader = teamLeader;
	}

	public int[] getAvgIntegral() {
		return avgIntegral;
	}

	public void setAvgIntegral(int[] avgIntegral) {
		this.avgIntegral = avgIntegral;
	}



}
