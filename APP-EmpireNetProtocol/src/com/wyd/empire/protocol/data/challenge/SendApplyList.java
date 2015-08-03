package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SendApplyList extends AbstractData {
	
	private	int[]	playerId	;//	玩家ID
	private	String[]	playerName	;//	玩家名称
	private	int[]	playerLevel	;//	玩家等级
	private	int[]	playerBattle	;//	玩家战斗力

	
    public SendApplyList(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_SendApplyList, sessionId, serial);
    }

    public SendApplyList() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_SendApplyList);
    }

	public int[] getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int[] playerId) {
		this.playerId = playerId;
	}

	public String[] getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}

	public int[] getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int[] playerLevel) {
		this.playerLevel = playerLevel;
	}

	public int[] getPlayerBattle() {
		return playerBattle;
	}

	public void setPlayerBattle(int[] playerBattle) {
		this.playerBattle = playerBattle;
	}
    
    
}
