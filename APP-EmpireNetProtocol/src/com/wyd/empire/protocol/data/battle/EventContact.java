package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class EventContact extends AbstractData {
    private int battleId;
    private int playerId; //玩家ID
    private int eventId;//事件ID
    public EventContact(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_EventContact, sessionId, serial);
	}

	public EventContact() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_EventContact);
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

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	

	
}
