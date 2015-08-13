package com.app.empire.protocol.data.server;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 帐号密码验证
 * @see AbstractData
 * @author mazheng
 */
public class LegacyVerification extends AbstractData {
    private int     accountId;
    private int     playerId;
	private String 	accountName;
    private String 	passWord;
    public LegacyVerification(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyVerification, sessionId, serial);
    }

    public LegacyVerification() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyVerification);
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
