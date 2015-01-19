package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
