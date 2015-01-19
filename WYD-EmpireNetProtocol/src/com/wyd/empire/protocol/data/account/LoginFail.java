package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 登录失败
 * @see AbstractData
 * @author mazheng
 */
public class LoginFail extends AbstractData {
    private String message;
    public LoginFail(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_LoginFail, sessionId, serial);
    }

    public LoginFail() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_LoginFail);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
