package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class TiroTask extends AbstractData {

	public TiroTask(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroTask, sessionId, serial);
	}

	public TiroTask() {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroTask);
	}
}
