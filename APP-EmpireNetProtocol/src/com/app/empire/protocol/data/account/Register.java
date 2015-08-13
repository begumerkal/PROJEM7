package com.app.empire.protocol.data.account;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 用户注册
 * @see AbstractData
 * @author mazheng
 */
public class Register extends AbstractData {
	private byte[] 	accountName;    // 帐号（加密后的字符串）
    private byte[] 	passWord;       // 密码（加密后的字符串）
    private String 	version;		// 用版本字串来区分不同的主渠道
    private String  model;          // 手机操作系统版本
    private int 	channel;	    // 子渠道标示
    private byte[]  email;          // 邮箱地址（加密后的字符串）
    private byte[]  inviteAccount;  // 邀请人（加密后的字符串）
    public Register(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Register, sessionId, serial);
    }

    public Register() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Register);
    }

    public byte[] getAccountName() {
        return accountName;
    }

    public void setAccountName(byte[] accountName) {
        this.accountName = accountName;
    }

    public byte[] getPassWord() {
        return passWord;
    }

    public void setPassWord(byte[] passWord) {
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

    public byte[] getEmail() {
        return email;
    }

    public void setEmail(byte[] email) {
        this.email = email;
    }

    public byte[] getInviteAccount() {
        return inviteAccount;
    }

    public void setInviteAccount(byte[] inviteAccount) {
        this.inviteAccount = inviteAccount;
    }
}
