package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetNearbyMailContent extends AbstractData {
    private int myInfoId;
	private int mailId;
	public GetNearbyMailContent(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyMailContent, sessionId, serial);
	}

	public GetNearbyMailContent() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyMailContent);
	}

	public int getMyInfoId() {
        return myInfoId;
    }

    public void setMyInfoId(int myInfoId) {
        this.myInfoId = myInfoId;
    }

    public int getMailId() {
		return mailId;
	}

	public void setMailId(int mailId) {
		this.mailId = mailId;
	}
}
