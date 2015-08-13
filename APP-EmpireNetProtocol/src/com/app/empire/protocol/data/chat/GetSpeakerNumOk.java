package com.app.empire.protocol.data.chat;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetSpeakerNumOk extends AbstractData {
	private int    speakNum;
	private int	   colorSpeakNum;
	public GetSpeakerNumOk(int sessionId, int serial) {
		super(Protocol.MAIN_CHAT, Protocol.CHAT_GetSpeakerNumOk, sessionId, serial);
	}

	public GetSpeakerNumOk() {
		super(Protocol.MAIN_CHAT, Protocol.CHAT_GetSpeakerNumOk);
	}

	public int getSpeakNum() {
		return speakNum;
	}

	public void setSpeakNum(int speakNum) {
		this.speakNum = speakNum;
	}

	public int getColorSpeakNum() {
		return colorSpeakNum;
	}

	public void setColorSpeakNum(int colorSpeakNum) {
		this.colorSpeakNum = colorSpeakNum;
	}


}
