package com.app.empire.world.session;

import io.netty.channel.Channel;
import io.netty.handler.timeout.IdleState;

import com.app.session.Session;

public class AuthSession extends Session {
	// private static final Logger log = Logger.getLogger(AuthSession.class);

	public AuthSession(Channel channel) {
		super(channel);
	}

	@Override
	public <AbstractData> void handle(AbstractData packet) {
	}

	@Override
	public void created() {
	}

 

	@Override
	public void opened() {
	}

	@Override
	public void closed() {
	}

	@Override
	public void idle(Channel channel, IdleState status) {
		close();
	}
}