package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class CreateRole extends AbstractData {
	private String nickname;
	private byte heroExtId;
	private String clientModel;// 手机型号
	private String systemName;// 手机系统
	private String systemVersion;// 系统版本

	public CreateRole(int sessionId, int serial) {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_CreateRole, sessionId, serial);
	}

	public CreateRole() {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_CreateRole);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public byte getHeroExtId() {
		return heroExtId;
	}

	public void setHeroExtId(byte heroExtId) {
		this.heroExtId = heroExtId;
	}

	public String getClientModel() {
		return clientModel;
	}

	public void setClientModel(String clientModel) {
		this.clientModel = clientModel;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

}
