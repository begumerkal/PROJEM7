package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>Login</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ACCOUNT下子命令ACCOUNT_Login(账户登录)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class Login extends AbstractData {
	private byte[] 	udid;
	private byte[] 	accountName;
    private byte[] 	passWord;
    private String 	version;		// 用版本字串来区分不同的主渠道
    private int 	channel;	    // 子渠道标示
    private int     isChannelLogon; // 第三方用户体系(0:自己的用户体系,1:第三方用户体系)
    private byte[]  oldUdid;        // 旧规则udid
    public Login(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Login, sessionId, serial);
    }

    public Login() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Login);
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

    public int getIsChannelLogon() {
        return isChannelLogon;
    }

    public void setIsChannelLogon(int isChannelLogon) {
        this.isChannelLogon = isChannelLogon;
    }

    public byte[] getOldUdid() {
        return oldUdid;
    }

    public void setOldUdid(byte[] oldUdid) {
        this.oldUdid = oldUdid;
    }
}
