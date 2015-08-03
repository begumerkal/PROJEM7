package com.wyd.empire.protocol.data.card;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class MeltCardOk extends AbstractData {
	public MeltCardOk(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_MeltCardOk, sessionId, serial);
    }
	public MeltCardOk(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_MeltCardOk);
	}
	
}
