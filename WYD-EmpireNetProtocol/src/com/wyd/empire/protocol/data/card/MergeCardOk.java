package com.wyd.empire.protocol.data.card;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

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
