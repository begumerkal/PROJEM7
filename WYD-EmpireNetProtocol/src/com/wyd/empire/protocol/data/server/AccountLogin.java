package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class AccountLogin extends AbstractData {

	private String name;
	private String password;
	private int channel; // 渠道ID
	private String ip;

	public AccountLogin(int sessionId, int serial) {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_AccountLogin, sessionId, serial);
	}

	public AccountLogin() {
		super(Protocol.MAIN_SERVER, Protocol.SERVER_AccountLogin);
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
