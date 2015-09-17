package com.app.dispatch;
import io.netty.channel.Channel;

import java.nio.ByteBuffer;

import com.app.protocol.INetData;
public abstract interface Dispatcher {
	public abstract void broadcast(ByteBuffer paramByteBuffer);

	public abstract void unRegisterClient(int paramInt);

	public abstract Channel getSession(int paramInt);

	public abstract void shutdown();
	
	public abstract void syncPlayer(INetData data);
}