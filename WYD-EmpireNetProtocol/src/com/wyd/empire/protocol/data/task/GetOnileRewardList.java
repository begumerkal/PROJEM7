package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetOnileRewardList extends AbstractData {
	
	public GetOnileRewardList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetOnileRewardList, sessionId, serial);
	}

	public GetOnileRewardList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetOnileRewardList);
	}

}
