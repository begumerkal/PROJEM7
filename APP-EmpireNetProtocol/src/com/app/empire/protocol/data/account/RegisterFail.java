package com.app.empire.protocol.data.account;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 用户注册失败
 * @see AbstractData
 * @author mazheng
 */
public class RegisterFail extends AbstractData {
    private String message;
    public RegisterFail(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RegisterFail, sessionId, serial);
    }

    public RegisterFail() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RegisterFail);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
