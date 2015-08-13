package com.app.empire.protocol.data.qualifying;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class DetailOk extends AbstractData {
	
	private String detail;
	
	public DetailOk(int sessionId, int serial) {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_DetailOk, sessionId, serial);
	}

	public DetailOk() {
		super(Protocol.MAIN_QUALIFYING, Protocol.QUALIFYING_DetailOk);
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
