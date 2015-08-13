package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class BuyVigorOk  extends AbstractData {
	private int vigor;
	public BuyVigorOk(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_BuyVigorOk, sessionId, serial);
    }
	public BuyVigorOk(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_BuyVigorOk);
	}
	public int getVigor() {
		return vigor;
	}
	public void setVigor(int vigor) {
		this.vigor = vigor;
	}

}

