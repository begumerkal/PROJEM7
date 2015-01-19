package com.wyd.empire.protocol.data.fund;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

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
