package com.app.empire.protocol.data.strengthen;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class ChangeAttributeOk extends AbstractData {
    public ChangeAttributeOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_ChangeAttributeOk, sessionId, serial);
    }

    public ChangeAttributeOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_ChangeAttributeOk);
    }
}
