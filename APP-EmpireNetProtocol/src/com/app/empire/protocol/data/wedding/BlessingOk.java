package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class BlessingOk extends AbstractData {

    public BlessingOk(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_BlessingOk, sessionId, serial);
    }

    public BlessingOk() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_BlessingOk);
    }
 
}
