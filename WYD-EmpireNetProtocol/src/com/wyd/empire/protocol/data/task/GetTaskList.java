package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetTaskList extends AbstractData {

	public GetTaskList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetTaskList, sessionId, serial);
	}

	public GetTaskList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetTaskList);
	}
}
