package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SupplSign extends AbstractData {
	
	public SupplSign(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_SupplSign, sessionId, serial);
	}

	public SupplSign() {
		super(Protocol.MAIN_TASK, Protocol.TASK_SupplSign);
	}
}
