package com.wyd.empire.protocol.data.system;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetNoviceRemarkOk extends AbstractData {
	private String noviceRemark;

	public GetNoviceRemarkOk(int sessionId, int serial) {
		super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetNoviceRemarkOk,
				sessionId, serial);
	}

	public GetNoviceRemarkOk() {
		super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetNoviceRemarkOk);
	}

	public String getNoviceRemark() {
		return noviceRemark;
	}

	public void setNoviceRemark(String noviceRemark) {
		this.noviceRemark = noviceRemark;
	}
}
