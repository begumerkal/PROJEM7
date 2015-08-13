package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class Sign extends AbstractData {
	
	public Sign(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_Sign, sessionId, serial);
	}

	public Sign() {
		super(Protocol.MAIN_TASK, Protocol.TASK_Sign);
	}

}
