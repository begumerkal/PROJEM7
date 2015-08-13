package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetRankList extends AbstractData {
	
	
    public GetRankList(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetRankList, sessionId, serial);
    }

    public GetRankList() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetRankList);
    }
}
