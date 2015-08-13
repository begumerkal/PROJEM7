package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ExchangeList extends AbstractData {
	public ExchangeList(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_ExchangeList, sessionId, serial);
    }
	public ExchangeList(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_ExchangeList);
	}
	
}
