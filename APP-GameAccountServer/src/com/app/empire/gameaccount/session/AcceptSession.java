package com.app.empire.gameaccount.session;
import io.netty.channel.Channel;
import io.netty.handler.timeout.IdleState;

import java.net.SocketAddress;

import com.app.protocol.data.AbstractData;
import com.app.session.Session;

public class AcceptSession extends Session {
	protected String worldServerId;
	protected boolean valid = false;

	public String getWorldServerId() {
		return worldServerId;
	}

	public void setWorldServerId(String worldServerId) {
		this.worldServerId = worldServerId;
	}

	public AcceptSession(Channel channel) {
		this.channel = channel;
	}

	public boolean isValid() {
		return this.valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void send(AbstractData message) {
		if (isConnected()) {
			write(message);
		}
	}

	public SocketAddress getRemoteAddress() {
		if (this.channel != null) {
			return this.channel.remoteAddress();
		}
		return null;
	}

	@Override
	public <T> void handle(T paramT) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void created() {
		// TODO Auto-generated method stub

	}

	@Override
	public void opened() {
		// TODO Auto-generated method stub

	}

	@Override
	public void idle(Channel channel, IdleState status) {
		System.out.println("关闭链接：" + channel + " worldServerId:" + worldServerId);
		channel.close();
	}
}
