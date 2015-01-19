package com.wyd.empire.protocol.data.singlemap;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetVigorPrice extends AbstractData {
	public GetVigorPrice(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetVigorPrice, sessionId, serial);
    }
	public GetVigorPrice(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetVigorPrice);
	}
	


}
