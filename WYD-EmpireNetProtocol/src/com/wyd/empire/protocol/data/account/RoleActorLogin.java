package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class RoleActorLogin extends AbstractData {
    private String playerName;
    private String macCode;
    public RoleActorLogin(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RoleActorLogin, sessionId, serial);
    }

    public RoleActorLogin() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RoleActorLogin);
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMacCode() {
        return macCode;
    }

    public void setMacCode(String macCode) {
        this.macCode = macCode;
    }
}
