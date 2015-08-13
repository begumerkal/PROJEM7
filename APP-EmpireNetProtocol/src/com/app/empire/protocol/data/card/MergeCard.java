package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class MergeCard extends AbstractData {
	private int id;//碎片ID
	public MergeCard(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_MergeCard, sessionId, serial);
    }
	public MergeCard(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_MergeCard);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
