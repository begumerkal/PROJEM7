package com.app.session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.timeout.IdleState;

import com.app.protocol.data.AbstractData;
import com.app.protocol.exception.ProtocolException;

public abstract class Session {
	protected Channel channel;
	protected int sessionId;

	public Session(Channel channel) {
		this.channel = channel;
	}

	public Session() {
	}

	public abstract <T> void handle(T paramT);

	public abstract void closed();

	public abstract void created();

	public abstract void opened();

	public abstract void idle(Channel channel, IdleState status);

	public void defaultHandle() {
	}

	public Channel getChannel() {
		return this.channel;
	}

	public void setIoSession(Channel channel) {
		this.channel = channel;
	}

	public boolean isConnected() {
		if (this.channel == null) {
			return false;
		}
		return this.channel.isActive();
	}

	public void close() {
		if (isConnected()) {
			this.channel.close();
		}
	}

	public void write(AbstractData seg) {
		this.channel.writeAndFlush(seg);
	}
	
	public void writeAndClose(AbstractData seg) {
		ChannelFuture f = this.channel.writeAndFlush(seg);
		f.addListener(ChannelFutureListener.CLOSE);
	}
	public void reply(AbstractData data) {
		write(data);
	}

	public int getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public void forward(AbstractData data, int sessionId) {
		data.setSessionId(sessionId);
		write(data);
	}

	public void sendError(ProtocolException e) {
	}
}