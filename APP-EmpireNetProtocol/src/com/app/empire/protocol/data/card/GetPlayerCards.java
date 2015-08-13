package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetPlayerCards extends AbstractData {
	private int playerId;//
	public GetPlayerCards(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_GetPlayerCards, sessionId, serial);
    }
	public GetPlayerCards(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_GetPlayerCards);
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
}
