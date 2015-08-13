package com.app.empire.protocol.data.account;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class ChannelLoginResult extends AbstractData {
    private String code;
    private String message;

    public ChannelLoginResult(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ChannelLoginResult, sessionId, serial);
    }

    public ChannelLoginResult() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ChannelLoginResult);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
