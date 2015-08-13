package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ExchangeOk extends AbstractData {
	public ExchangeOk(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_ExchangeOk, sessionId, serial);
    }
	public ExchangeOk(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_ExchangeOk);
	}
	
	
}
