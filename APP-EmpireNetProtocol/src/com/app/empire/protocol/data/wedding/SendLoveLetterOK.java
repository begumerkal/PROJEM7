package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
