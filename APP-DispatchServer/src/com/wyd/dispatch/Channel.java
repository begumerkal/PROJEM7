package com.wyd.dispatch;

import java.util.Vector;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

/*
 * 链接存储类
 * */
public class Channel {
	private String name;
	private Vector<IoSession> sessions = new Vector<IoSession>();

	public Channel(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void join(IoSession session) {
		this.sessions.add(session);
	}

	public boolean removeSession(IoSession session) {
		return this.sessions.remove(session);
	}
	/* 广播 */
	public void broadcast(IoBuffer buffer) {
		for (IoSession session : this.sessions)
			session.write(buffer.duplicate());
	}
}