package com.wyd.empire.protocol.data.vip;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetVipPrivilegeInfo extends AbstractData {
	
	public GetVipPrivilegeInfo(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList, sessionId, serial);
	}

	public GetVipPrivilegeInfo() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetEverydayRewardList);
	}

}
