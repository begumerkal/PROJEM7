package com.wyd.empire.protocol.data.account;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 修改密码失败
 * @see AbstractData
 * @author mazheng
 */
public class FindPasswordOk extends AbstractData {
    private String message;
    public FindPasswordOk(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_FindPasswordOk, sessionId, serial);
    }

    public FindPasswordOk() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_FindPasswordOk);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
