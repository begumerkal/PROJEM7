package com.app.empire.protocol.data.player;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class RechargeCritResultOk extends AbstractData{
	private boolean result;
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public RechargeCritResultOk(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_RechargeCritResultOk, sessionId, serial);
	}
	  public RechargeCritResultOk() {
	        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_RechargeCritResultOk);
	    }

}
