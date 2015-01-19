package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SupplSign extends AbstractData {
	
	public SupplSign(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_SupplSign, sessionId, serial);
	}

	public SupplSign() {
		super(Protocol.MAIN_TASK, Protocol.TASK_SupplSign);
	}
}
