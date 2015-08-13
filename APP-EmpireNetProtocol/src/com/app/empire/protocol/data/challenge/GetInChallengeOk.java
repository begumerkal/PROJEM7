package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetInChallengeOk extends AbstractData {
	
	private	int	playerId;	//玩家ID
	private	int	integral;	//我的积分
	private	int[]	reward;	//奖励ID
	private	String[]	name;	//三强名称
	private	String[]	teamName;	//三强战队名称
	private String		detail;		//规则
	private boolean 	startOr;	//是否已开启
	private int lastIntegral;

	
    public GetInChallengeOk(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetInChallengeOk, sessionId, serial);
    }

    public GetInChallengeOk() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetInChallengeOk);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	

	public int[] getReward() {
		return reward;
	}

	public void setReward(int[] reward) {
		this.reward = reward;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getTeamName() {
		return teamName;
	}

	public void setTeamName(String[] teamName) {
		this.teamName = teamName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean isStartOr() {
		return startOr;
	}

	public void setStartOr(boolean startOr) {
		this.startOr = startOr;
	}

	public int getLastIntegral() {
		return lastIntegral;
	}

	public void setLastIntegral(int lastIntegral) {
		this.lastIntegral = lastIntegral;
	}

}
