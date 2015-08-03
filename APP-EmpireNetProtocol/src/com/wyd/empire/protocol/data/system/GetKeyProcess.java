package com.wyd.empire.protocol.data.system;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetKeyProcess extends AbstractData {
	public GetKeyProcess(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetKeyProcess, sessionId, serial);
    }

    public GetKeyProcess() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetKeyProcess);
    }
}
