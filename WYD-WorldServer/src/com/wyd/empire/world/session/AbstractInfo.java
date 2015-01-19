package com.wyd.empire.world.session;

import com.wyd.protocol.data.AbstractData;
import com.wyd.session.Session;

public class AbstractInfo {
	private AbstractData dataobj;
	private Session session;
	private long createTimes;
	// private String sessionKey;
	private int sessionId;

	public AbstractInfo(AbstractData dataobj, Session session) {
		this.dataobj = dataobj;
		this.session = session;
		// this.sessionKey = session.getSessionId() + "-" +
		// dataobj.getSessionId();
		this.sessionId = dataobj.getSessionId();
		this.createTimes = System.currentTimeMillis();
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

	// public String getSessionKey() {
	// return sessionKey;
	// }

	/**
	 * 协议在队列中超过15秒则不在处理
	 * 
	 * @return
	 */
	public boolean Check() {
		long time = System.currentTimeMillis() - createTimes;
		return time < 15000;
	}
}
