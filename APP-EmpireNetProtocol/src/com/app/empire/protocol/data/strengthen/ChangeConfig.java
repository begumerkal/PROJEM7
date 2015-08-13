package com.app.empire.protocol.data.strengthen;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class ChangeConfig extends AbstractData {  

    public ChangeConfig(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_ChangeConfig, sessionId, serial);
    }

    public ChangeConfig() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_ChangeConfig);
    }
}
