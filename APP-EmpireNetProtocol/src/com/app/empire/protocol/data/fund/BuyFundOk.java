package com.app.empire.protocol.data.fund;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

/**
 * 购买基金成功 
 * @author sunzx
 *
 */
public class BuyFundOk extends AbstractData {
    private boolean result;  // 购买结果
    private String  message; // 提示信息
    
    public BuyFundOk(int sessionId, int serial) {
        super(Protocol.MAIN_FUND, Protocol.FUND_BuyFundOk, sessionId, serial);
    }

    public BuyFundOk() {
        super(Protocol.MAIN_FUND, Protocol.FUND_BuyFundOk);
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
