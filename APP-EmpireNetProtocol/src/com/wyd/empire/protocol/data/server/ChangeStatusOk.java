package com.wyd.empire.protocol.data.server;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class ChangeStatusOk extends AbstractData {
    private String accountName;
    public ChangeStatusOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ChangeStatusOk, sessionId, serial);
    }

    public ChangeStatusOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ChangeStatusOk);
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }
}
