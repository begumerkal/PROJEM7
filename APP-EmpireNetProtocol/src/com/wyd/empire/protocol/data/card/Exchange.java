package com.wyd.empire.protocol.data.card;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Exchange extends AbstractData {
	private int id;//
	public Exchange(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_Exchange, sessionId, serial);
    }
	public Exchange(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_Exchange);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
