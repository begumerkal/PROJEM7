package com.wyd.empire.protocol.data.nearby;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SendNearbyMailCount extends AbstractData {
    private int playerId;
    private int infoId;
    private int mailCount;
	public SendNearbyMailCount(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_SendNearbyMailCount, sessionId, serial);
	}

	public SendNearbyMailCount() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_SendNearbyMailCount);
	}

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public int getMailCount() {
        return mailCount;
    }

    public void setMailCount(int mailCount) {
        this.mailCount = mailCount;
    }
}
