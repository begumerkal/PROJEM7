package com.wyd.empire.protocol.data.server;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 用户注册
 * @see AbstractData
 * @author mazheng
 */
public class LegacyFindPassword extends AbstractData {
    private int     accountId;
    private int     playerId;
    private String  email;          // 邮箱地址（加密后的字符串
    public LegacyFindPassword(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyFindPassword, sessionId, serial);
    }

    public LegacyFindPassword() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyFindPassword);
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
}
