package com.wyd.empire.protocol.data.chat;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetSpeakerNum extends AbstractData {
	private int    playerId;
	public GetSpeakerNum(int sessionId, int serial) {
		super(Protocol.MAIN_CHAT, Protocol.CHAT_GetSpeakerNum, sessionId, serial);
	}

	public GetSpeakerNum() {
		super(Protocol.MAIN_CHAT, Protocol.CHAT_GetSpeakerNum);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

}
