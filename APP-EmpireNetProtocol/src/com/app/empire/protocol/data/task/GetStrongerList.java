package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetStrongerList extends AbstractData {

	public GetStrongerList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetStrongerList, sessionId, serial);
	}

	public GetStrongerList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetStrongerList);
	}
}
