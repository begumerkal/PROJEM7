package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ChallengeFail extends AbstractData {
	public ChallengeFail(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_ChallengeFail, sessionId, serial);
    }
	public ChallengeFail(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_ChallengeFail);
	}
	

}
