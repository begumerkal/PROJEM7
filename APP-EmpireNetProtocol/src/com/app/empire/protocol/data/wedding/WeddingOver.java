package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class WeddingOver extends AbstractData {

    public WeddingOver(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_WeddingOver, sessionId, serial);
    }

    public WeddingOver() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_WeddingOver);
    }
 
}
