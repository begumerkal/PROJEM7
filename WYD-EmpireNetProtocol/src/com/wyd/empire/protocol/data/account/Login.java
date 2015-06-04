package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>Login</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ACCOUNT下子命令ACCOUNT_Login(账户登录)对应数据封装。
 * 
 * @see AbstractData
 * @author doter
 */
public class Login extends AbstractData {
	private String 	udid;
	private String 	accountName;
    private String 	passWord;
    private String 	version;		// 用版本字串来区分不同的主渠道
    private int 	channel;	    // 子渠道标示
    public Login(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Login, sessionId, serial);
    }

    public Login() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Login);
    }

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
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

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

}
