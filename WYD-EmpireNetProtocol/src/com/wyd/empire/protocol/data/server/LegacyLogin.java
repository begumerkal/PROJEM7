package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class LegacyLogin extends AbstractData {
	private String name;
	private String password;
	private int channel; // 渠道ID

	public LegacyLogin(int sessionId, int serial) {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyLogin, sessionId, serial);
	}

	public LegacyLogin() {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyLogin);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}
}
