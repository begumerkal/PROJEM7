package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetLevelRewardList extends AbstractData {
	
	public GetLevelRewardList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList, sessionId, serial);
	}

	public GetLevelRewardList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList);
	}

}
