package com.wyd.empire.protocol.data.practice;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 激活修炼
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class ActivatePractice extends AbstractData {
	
    public ActivatePractice(int sessionId, int serial) {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_ActivatePractice, sessionId, serial);
    }

    public ActivatePractice() {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_ActivatePractice);
    }
}
