package com.app.empire.protocol.data.server;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 用户注册
 * @see AbstractData
 * @author mazheng
 */
public class LegacyRegister extends AbstractData {
    private int     accountId;
    private int     playerId;
	private String 	accountName;    // 帐号（加密后的字符串）
    private String 	passWord;       // 密码（加密后的字符串）
    private String 	version;		// 用版本字串来区分不同的主渠道
    private String  model;          // 手机操作系统版本
    private int 	channel;	    // 子渠道标示
    private String  email;          // 邮箱地址（加密后的字符串）
    private String  inviteAccount;  // 邀请人（加密后的字符串）
    public LegacyRegister(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyRegister, sessionId, serial);
    }

    public LegacyRegister() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyRegister);
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInviteAccount() {
        return inviteAccount;
    }

    public void setInviteAccount(String inviteAccount) {
        this.inviteAccount = inviteAccount;
    }
}
