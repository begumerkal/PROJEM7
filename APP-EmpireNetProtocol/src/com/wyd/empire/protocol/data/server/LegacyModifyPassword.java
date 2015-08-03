package com.wyd.empire.protocol.data.server;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 用户注册
 * @see AbstractData
 * @author mazheng
 */
public class LegacyModifyPassword extends AbstractData {
    private int     accountId;
    private int     playerId;
	private String 	oldPassWold;
    private String 	newPassWord;
    public LegacyModifyPassword(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyModifyPassword, sessionId, serial);
    }

    public LegacyModifyPassword() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyModifyPassword);
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

    public String getOldPassWold() {
        return oldPassWold;
    }

    public void setOldPassWold(String oldPassWold) {
        this.oldPassWold = oldPassWold;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }
}
