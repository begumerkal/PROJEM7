package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetLevelRewardList extends AbstractData {
	
	public GetLevelRewardList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList, sessionId, serial);
	}

	public GetLevelRewardList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList);
	}

}
