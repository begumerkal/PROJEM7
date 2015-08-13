package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class MeltCardOk extends AbstractData {
	public MeltCardOk(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_MeltCardOk, sessionId, serial);
    }
	public MeltCardOk(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_MeltCardOk);
	}
	
}
