package com.wyd.empire.protocol.data.player;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

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
