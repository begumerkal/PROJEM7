package com.wyd.empire.protocol.data.system;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetIslandState extends AbstractData {

    public GetIslandState(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetIslandState, sessionId, serial);
    }

    public GetIslandState() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetIslandState);
    }
}
