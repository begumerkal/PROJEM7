package com.app.empire.protocol.data.square;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SendInfo extends AbstractData {

	private String squreUrl;

    public SendInfo(int sessionId, int serial) {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_SendInfo, sessionId, serial);
    }

    public SendInfo() {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_SendInfo);
    }

	public String getSqureUrl() {
		return squreUrl;
	}

	public void setSqureUrl(String squreUrl) {
		this.squreUrl = squreUrl;
	}

}
