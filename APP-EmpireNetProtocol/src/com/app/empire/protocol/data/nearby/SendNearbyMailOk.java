package com.app.empire.protocol.data.nearby;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class SendNearbyMailOk extends AbstractData {
    private int playerId;

    public SendNearbyMailOk(int sessionId, int serial) {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_SendNearbyMailOk, sessionId, serial);
    }

    public SendNearbyMailOk() {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_SendNearbyMailOk);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
