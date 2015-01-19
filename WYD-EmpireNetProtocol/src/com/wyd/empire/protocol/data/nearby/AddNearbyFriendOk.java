package com.wyd.empire.protocol.data.nearby;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class AddNearbyFriendOk extends AbstractData {
    private int playerId;

    public AddNearbyFriendOk(int sessionId, int serial) {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_AddNearbyFriendOk, sessionId, serial);
    }

    public AddNearbyFriendOk() {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_AddNearbyFriendOk);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
