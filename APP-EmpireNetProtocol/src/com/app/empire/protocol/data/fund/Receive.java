package com.app.empire.protocol.data.fund;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

/**
 * 领取基金协议
 * @author sunzx
 *
 */
public class Receive extends AbstractData {
    
    public Receive(int sessionId, int serial) {
        super(Protocol.MAIN_FUND, Protocol.FUND_Receive, sessionId, serial);
    }

    public Receive() {
        super(Protocol.MAIN_FUND, Protocol.FUND_Receive);
    }
}
