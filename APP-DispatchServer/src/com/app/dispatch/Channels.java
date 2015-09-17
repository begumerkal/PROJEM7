package com.app.dispatch;

import java.nio.ByteBuffer;
import java.util.Vector;
import io.netty.channel.Channel;

/*
 * 链接存储类
 * */
public class Channels {
	private String name;
	private Vector<Channel> sessions = new Vector<Channel>();

	public Channels(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void join(Channel channel) {
		this.sessions.add(channel);
	}

	public boolean removeSession(Channel session) {
		return this.sessions.remove(session);
	}
	/* 广播 */
	public void broadcast(ByteBuffer buffer) {
		for (Channel session : this.sessions)
			session.write(buffer.duplicate());
	}
}