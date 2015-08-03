package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetEverydayRewardList extends AbstractData {
	
	public GetEverydayRewardList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList, sessionId, serial);
	}

	public GetEverydayRewardList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList);
	}

}
