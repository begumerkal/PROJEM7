package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>Login</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ACCOUNT下子命令ACCOUNT_Login(账户登录)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class LoginAgain extends AbstractData {
	private byte[] 	udid;
	private byte[] 	accountName;
    private byte[] 	passWord;
    private String 	version;// 用版本字串来区分不同的主渠道
    private int 	channel;// 子渠道标示
    private String 	playerName;

    public LoginAgain(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_LoginAgain, sessionId, serial);
    }

    public LoginAgain() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_LoginAgain);
    }

	public byte[] getUdid() {
		return udid;
	}

	public void setUdid(byte[] udid) {
		this.udid = udid;
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

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
