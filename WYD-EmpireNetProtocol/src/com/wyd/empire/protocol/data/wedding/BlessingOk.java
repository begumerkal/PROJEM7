package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
