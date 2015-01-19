package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class ServerLogin extends AbstractData {
    private String id;
    private String password;

    public ServerLogin(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ServerLogin, sessionId, serial);
    }

    public ServerLogin() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_ServerLogin);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
