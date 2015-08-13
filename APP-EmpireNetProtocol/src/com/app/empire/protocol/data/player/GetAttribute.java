package com.app.empire.protocol.data.player;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetAttribute extends AbstractData {
    public GetAttribute(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetAttribute, sessionId, serial);
    }

    public GetAttribute() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetAttribute);
    }
}
