package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SendRankList extends AbstractData {
	
	private	String[]	playerName	;//	玩家名称
	private	int[]	integral	;//	玩家积分
	private	String[]	areaName	;//	服务器名称

	
    public SendRankList(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_SendRankList, sessionId, serial);
    }

    public SendRankList() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_SendRankList);
    }

	public String[] getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}

	public int[] getIntegral() {
		return integral;
	}

	public void setIntegral(int[] integral) {
		this.integral = integral;
	}

	public String[] getAreaName() {
		return areaName;
	}

	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
	}
}
