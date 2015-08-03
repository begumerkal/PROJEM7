package com.wyd.empire.protocol.data.account;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

/**
 * 用户重复登录账号
 * @author sunzx
 *
 */
public class RepeatLogin extends AbstractData {
    private String message;
    
    public RepeatLogin(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RepeatLogin, sessionId, serial);
    }

    public RepeatLogin() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RepeatLogin);
    }

    public RepeatLogin(String message) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RepeatLogin);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
