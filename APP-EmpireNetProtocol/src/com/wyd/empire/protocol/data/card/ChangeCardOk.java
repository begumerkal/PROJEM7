package com.wyd.empire.protocol.data.card;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ChangeCardOk extends AbstractData {
	
	public ChangeCardOk(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_ChangeCardOk, sessionId, serial);
    }
	public ChangeCardOk(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_ChangeCardOk);
	}
}
