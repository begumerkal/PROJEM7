package com.wyd.empire.protocol.data.useitems;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class UseObjectOK extends AbstractData {
	private int status;
	
	public UseObjectOK(int sessionId, int serial) {
        super(Protocol.MAIN_USEITEMS, Protocol.USEITEMS_UseObjectOK, sessionId, serial);
    }

    public UseObjectOK() {
        super(Protocol.MAIN_USEITEMS, Protocol.USEITEMS_UseObjectOK);
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	 
}
