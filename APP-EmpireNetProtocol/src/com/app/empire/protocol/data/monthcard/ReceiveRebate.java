package com.app.empire.protocol.data.monthcard;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 领取今日返利
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class ReceiveRebate extends AbstractData {
	

	
    public ReceiveRebate(int sessionId, int serial) {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_ReceiveRebate, sessionId, serial);
    }

    public ReceiveRebate() {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_ReceiveRebate);
    }

    
}
