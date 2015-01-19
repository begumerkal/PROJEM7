package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 用户注册结果
 * 
 * @see AbstractData
 * @author mazheng
 */
public class LegacyFindPasswordOk extends AbstractData {
    private int      playerId;
    private String   email;
    private String[] account;
    private String[] password;

    public LegacyFindPasswordOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyFindPasswordOk, sessionId, serial);
    }

    public LegacyFindPasswordOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyFindPasswordOk);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getAccount() {
        return account;
    }

    public void setAccount(String[] account) {
        this.account = account;
    }

    public String[] getPassword() {
        return password;
    }

    public void setPassword(String[] password) {
        this.password = password;
    }
}
