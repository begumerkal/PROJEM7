package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UpdatePlayerLocationInfo extends AbstractData {
	private int longitude;
	private int latitude;

	public UpdatePlayerLocationInfo(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_UpdatePlayerLocationInfo, sessionId, serial);
	}

	public UpdatePlayerLocationInfo() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_UpdatePlayerLocationInfo);
	}

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
}
