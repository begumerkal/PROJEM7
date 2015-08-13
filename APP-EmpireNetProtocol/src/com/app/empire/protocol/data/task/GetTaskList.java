package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetTaskList extends AbstractData {

	public GetTaskList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetTaskList, sessionId, serial);
	}

	public GetTaskList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetTaskList);
	}
}
