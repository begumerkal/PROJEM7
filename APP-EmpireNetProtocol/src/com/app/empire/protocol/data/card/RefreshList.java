package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class RefreshList extends AbstractData {
	public RefreshList(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_RefreshList, sessionId, serial);
    }
	public RefreshList(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_RefreshList);
	}
	
	
}
