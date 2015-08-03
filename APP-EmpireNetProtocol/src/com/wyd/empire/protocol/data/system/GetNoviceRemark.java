package com.wyd.empire.protocol.data.system;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetNoviceRemark extends AbstractData {

    public GetNoviceRemark(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetNoviceRemark, sessionId, serial);
    }

    public GetNoviceRemark() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetNoviceRemark);
    }
}
