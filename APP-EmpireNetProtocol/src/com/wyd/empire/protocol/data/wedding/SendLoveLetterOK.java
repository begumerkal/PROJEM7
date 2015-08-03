package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendLoveLetterOK extends AbstractData {

    public SendLoveLetterOK(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendLoveLetterOK, sessionId, serial);
    }

    public SendLoveLetterOK() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendLoveLetterOK);
    }

}
