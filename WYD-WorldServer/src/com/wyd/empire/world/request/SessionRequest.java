package com.wyd.empire.world.request;

import com.wyd.empire.world.session.ConnectSession;
import com.wyd.net.IRequest;
import com.wyd.session.Session;

public class SessionRequest implements IRequest {
	private int type;
	private int id;
	private int sessionId;
	private Session session;

	public SessionRequest(int type, int id, int sessionId, Session session) {
		this.type = type;
		this.id = id;
		this.sessionId = sessionId;
		this.session = session;
	}

	public int getSessionId() {
		return this.sessionId;
	}

	public ConnectSession getConnectionSession() {
		if (this.session instanceof ConnectSession) {
			return (ConnectSession) this.session;
		}
		return null;
	}

	public int getId() {
		return this.id;
	}

	public int getType() {
		return this.type;
	}
}