package com.wyd.empire.protocol.data.singlemap;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ChallengeFail extends AbstractData {
	public ChallengeFail(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_ChallengeFail, sessionId, serial);
    }
	public ChallengeFail(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_ChallengeFail);
	}
	

}
