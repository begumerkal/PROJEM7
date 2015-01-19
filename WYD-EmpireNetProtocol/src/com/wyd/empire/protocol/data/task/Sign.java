package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Sign extends AbstractData {
	
	public Sign(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_Sign, sessionId, serial);
	}

	public Sign() {
		super(Protocol.MAIN_TASK, Protocol.TASK_Sign);
	}

}
