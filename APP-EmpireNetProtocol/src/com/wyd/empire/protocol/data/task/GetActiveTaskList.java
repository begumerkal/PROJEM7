package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetActiveTaskList extends AbstractData {

	public GetActiveTaskList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetActiveTaskList, sessionId, serial);
	}

	public GetActiveTaskList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetActiveTaskList);
	}
}
