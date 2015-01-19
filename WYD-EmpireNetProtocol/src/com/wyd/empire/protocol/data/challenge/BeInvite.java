package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class BeInvite extends AbstractData {
	
	private int battleTeamId;//	战队ID（相当于战斗时的房间ID）
	private String playerName;//	String	邀请人名称
	private String teamName;//	战队名称（房间名称）
	private int	invitePlayerId; // 申请人ID


	
    public BeInvite(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_BeInvite, sessionId, serial);
    }

    public BeInvite() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_BeInvite);
    }

	public int getBattleTeamId() {
		return battleTeamId;
	}

	public void setBattleTeamId(int battleTeamId) {
		this.battleTeamId = battleTeamId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getInvitePlayerId() {
		return invitePlayerId;
	}

	public void setInvitePlayerId(int invitePlayerId) {
		this.invitePlayerId = invitePlayerId;
	}

}
