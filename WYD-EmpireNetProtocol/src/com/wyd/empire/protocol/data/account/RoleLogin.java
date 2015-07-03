package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class RoleLogin extends AbstractData {
    private String nickname;
    private String macCode;
    public RoleLogin(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RoleLogin, sessionId, serial);
    }

    public RoleLogin() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RoleLogin);
    }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMacCode() {
		return macCode;
	}

	public void setMacCode(String macCode) {
		this.macCode = macCode;
	}

 
}
