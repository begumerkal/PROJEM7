package com.app.empire.protocol.data.account;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 用户注册成功
 * @see AbstractData
 * @author mazheng
 */
public class RegisterOk extends AbstractData {
    public RegisterOk(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RegisterOk, sessionId, serial);
    }

    public RegisterOk() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RegisterOk);
    }
}
