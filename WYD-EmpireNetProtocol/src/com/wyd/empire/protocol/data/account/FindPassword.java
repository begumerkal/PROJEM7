package com.wyd.empire.protocol.data.account;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 找回密码
 * @see AbstractData
 * @author mazheng
 */
public class FindPassword extends AbstractData {
    private String email;
    public FindPassword(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_FindPassword, sessionId, serial);
    }

    public FindPassword() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_FindPassword);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
