package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetStrongerList extends AbstractData {

	public GetStrongerList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetStrongerList, sessionId, serial);
	}

	public GetStrongerList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetStrongerList);
	}
}
