package com.wyd.empire.protocol.data.nearby;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetNearbyMailCount extends AbstractData {
    private int myInfoId;
	public GetNearbyMailCount(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyMailCount, sessionId, serial);
	}

	public GetNearbyMailCount() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyMailCount);
	}

	public int getMyInfoId() {
        return myInfoId;
    }

    public void setMyInfoId(int myInfoId) {
        this.myInfoId = myInfoId;
    }
}
