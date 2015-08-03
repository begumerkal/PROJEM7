package com.wyd.empire.protocol.data.card;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ExchangeOk extends AbstractData {
	public ExchangeOk(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_ExchangeOk, sessionId, serial);
    }
	public ExchangeOk(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_ExchangeOk);
	}
	
	
}
