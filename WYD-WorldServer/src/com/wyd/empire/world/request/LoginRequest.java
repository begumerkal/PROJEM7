package com.wyd.empire.world.request;

import com.wyd.empire.world.session.ConnectSession;

public class LoginRequest extends SessionRequest {
	protected String version;
	protected String name;
	protected String password;
	protected boolean isRelogin;
	protected String playerName;
	protected int subChannel;

	public LoginRequest(int id, int sessionId, ConnectSession session, String name, String password, String version, int subChannel,
			boolean isRelogin, String playerName) {
		super(IRequestType.LOGIN, id, sessionId, session);
		this.version = version;
		this.subChannel = subChannel;
		this.name = name;
		this.password = password;
		this.isRelogin = isRelogin;
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public boolean isReglogin() {
		return this.isRelogin;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	public String getVersion() {
		return this.version;
	}

	public int getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(int subChannel) {
		this.subChannel = subChannel;
	}
}