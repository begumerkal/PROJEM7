package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class TiroTask extends AbstractData {

	public TiroTask(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroTask, sessionId, serial);
	}

	public TiroTask() {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroTask);
	}
}
