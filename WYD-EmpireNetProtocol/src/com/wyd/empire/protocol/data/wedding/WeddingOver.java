package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
