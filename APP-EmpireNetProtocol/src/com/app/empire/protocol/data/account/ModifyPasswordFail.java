package com.app.empire.protocol.data.account;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 修改密码失败
 * @see AbstractData
 * @author mazheng
 */
public class ModifyPasswordFail extends AbstractData {
    private String message;
    public ModifyPasswordFail(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ModifyPasswordFail, sessionId, serial);
    }

    public ModifyPasswordFail() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ModifyPasswordFail);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
