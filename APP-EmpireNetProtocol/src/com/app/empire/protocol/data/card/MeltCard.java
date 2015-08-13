package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class MeltCard extends AbstractData {
	private int id;//卡牌ID
	public MeltCard(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_MeltCard, sessionId, serial);
    }
	public MeltCard(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_MeltCard);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
