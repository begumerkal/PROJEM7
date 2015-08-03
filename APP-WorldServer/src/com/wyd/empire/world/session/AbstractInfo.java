package com.wyd.empire.world.session;

import com.wyd.protocol.data.AbstractData;
import com.wyd.session.Session;

public class AbstractInfo {
	private AbstractData dataobj;
	private Session session;
	private int sessionId;
	// private String sessionKey;
	
	public AbstractInfo(AbstractData dataobj, Session session) {
		this.dataobj = dataobj;
		this.session = session;
		this.sessionId = dataobj.getSessionId();
		// this.sessionKey = session.getSessionId() + "-" +
		// dataobj.getSessionId();
	}

	public AbstractData getDataobj() {
		return dataobj;
	}

	public ConnectSession getSession() {
		return (ConnectSession) session;
	}

	public int getSessionId() {
		return sessionId;
	}

}
