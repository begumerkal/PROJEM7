package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class OtherEventContact extends AbstractData {
    private int battleId;
    private int playerId; //玩家ID
    private int eventId;//事件ID
    public OtherEventContact(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherEventContact, sessionId, serial);
	}

	public OtherEventContact() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_OtherEventContact);	}
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

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

		
}
