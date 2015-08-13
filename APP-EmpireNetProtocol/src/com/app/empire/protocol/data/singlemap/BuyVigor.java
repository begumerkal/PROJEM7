package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class BuyVigor  extends AbstractData {
	public BuyVigor(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetVigorPrice, sessionId, serial);
    }
	public BuyVigor(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetVigorPrice);
	}

}
