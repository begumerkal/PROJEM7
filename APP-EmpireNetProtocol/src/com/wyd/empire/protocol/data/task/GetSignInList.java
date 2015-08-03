package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetSignInList extends AbstractData {
	
	public GetSignInList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetSignInList, sessionId, serial);
	}

	public GetSignInList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetSignInList);
	}
}
