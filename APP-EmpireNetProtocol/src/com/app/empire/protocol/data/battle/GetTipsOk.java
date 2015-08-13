package com.app.empire.protocol.data.battle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetTipsOk extends AbstractData {
	private String[] tips;
	
	public GetTipsOk(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GetTipsOk, sessionId, serial);
	}

	public GetTipsOk() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GetTipsOk);
	}

	public String[] getTips() {
		return tips;
	}

	public void setTips(String[] tips) {
		this.tips = tips;
	}
	
}
