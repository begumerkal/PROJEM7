package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetEverydayRewardList extends AbstractData {
	
	public GetEverydayRewardList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList, sessionId, serial);
	}

	public GetEverydayRewardList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList);
	}

}
