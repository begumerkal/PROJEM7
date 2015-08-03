package com.wyd.empire.protocol.data.system;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetSystemInfo extends AbstractData {
    public GetSystemInfo(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetSystemInfo, sessionId, serial);
    }

    public GetSystemInfo() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetSystemInfo);
    }
}
