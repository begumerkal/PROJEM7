package com.app.empire.protocol.data.chat;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ChangeChannel extends AbstractData {
	private int channelId;// 要添加的频道id

	public ChangeChannel(int sessionId, int serial) {
		super(Protocol.MAIN_CHAT, Protocol.CHAT_ChangeChannel, sessionId, serial);
	}

	public ChangeChannel() {
		super(Protocol.MAIN_CHAT, Protocol.CHAT_ChangeChannel);
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
}
