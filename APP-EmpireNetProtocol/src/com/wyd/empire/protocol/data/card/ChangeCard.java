package com.wyd.empire.protocol.data.card;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ChangeCard extends AbstractData {
	private int id;
	public ChangeCard(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_ChangeCard, sessionId, serial);
    }
	public ChangeCard(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_ChangeCard);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
