package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class MergeCardOk extends AbstractData {
	private int   id;	
	public MergeCardOk(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_MergeCardOk, sessionId, serial);
    }
	public MergeCardOk(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_MergeCardOk);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
