package com.app.dispatch;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelService {
	private ConcurrentHashMap<String, Channels> channels = new ConcurrentHashMap<String, Channels>();
	private Channels worldChannel = new Channels("WORLD");

	public ChannelService() {
		this.channels.put(this.worldChannel.getName(), this.worldChannel);
	}

	public Channels getWorldChannel() {
		return this.worldChannel;
	}

	public Channels getAndCreate(String name) {
		Channels channels = new Channels(name);
		Channels c = this.channels.putIfAbsent(name, channels);
		if (c == null) {
			c = channels;
		}
		return c;
	}

	public Channels getChannel(String name) {
		return this.channels.get(name);
	}

	public void removeSessionFromAllChannel(Channel channel) {
		for (Channels channels : this.channels.values())
			channels.removeSession(channel);
	}
}