package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetVigorPrice extends AbstractData {
	public GetVigorPrice(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetVigorPrice, sessionId, serial);
    }
	public GetVigorPrice(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetVigorPrice);
	}
	


}
