package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class SetClientInfo extends AbstractData {
    private int accountId;
    private String clientModel;
    private String systemName;
    private String systemVersion;

    public SetClientInfo(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_SetClientInfo, sessionId, serial);
    }

    public SetClientInfo() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_SetClientInfo);
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getClientModel() {
        return clientModel;
    }

    public void setClientModel(String clientModel) {
        this.clientModel = clientModel;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }
}
