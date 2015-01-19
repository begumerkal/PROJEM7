package com.wyd.empire.protocol.data.challenge;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetRankList extends AbstractData {
	
	
    public GetRankList(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetRankList, sessionId, serial);
    }

    public GetRankList() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetRankList);
    }
}
