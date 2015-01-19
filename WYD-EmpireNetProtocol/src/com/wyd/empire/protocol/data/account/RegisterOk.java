package com.wyd.empire.protocol.data.account;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
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
