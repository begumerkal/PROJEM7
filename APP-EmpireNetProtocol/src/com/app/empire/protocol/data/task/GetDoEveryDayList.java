package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetDoEveryDayList extends AbstractData {

	public GetDoEveryDayList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetDoEveryDayList, sessionId, serial);
	}

	public GetDoEveryDayList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetDoEveryDayList);
	}
}
