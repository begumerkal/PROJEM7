package com.app.empire.protocol.data.fund;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

/**
 * 领取基金成功
 * @author sunzx
 *
 */
public class ReceiveOk extends AbstractData {
    private boolean result;
    private String  message;
    
    public ReceiveOk(int sessionId, int serial) {
        super(Protocol.MAIN_FUND, Protocol.FUND_ReceiveOk, sessionId, serial);
    }

    public ReceiveOk() {
        super(Protocol.MAIN_FUND, Protocol.FUND_ReceiveOk);
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
