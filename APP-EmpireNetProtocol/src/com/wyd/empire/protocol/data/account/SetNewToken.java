package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>Login</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ACCOUNT下子命令ACCOUNT_Login(账户登录)对应数据封装。
 * 
 * @see AbstractData
 * @author chenjie
 */
public class SetNewToken extends AbstractData {
	private String 	udid; //信鸽注册accessId
	private String 	token; //设备的token
	private int		deviceType; // 设备类型: 0为IOS 1为安卓
	
    public SetNewToken(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_SetNewToken, sessionId, serial);
    }

    public SetNewToken() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_SetNewToken);
    }

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	
	
}
