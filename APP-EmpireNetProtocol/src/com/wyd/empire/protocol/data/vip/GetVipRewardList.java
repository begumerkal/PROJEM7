package com.wyd.empire.protocol.data.vip;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetVipRewardList extends AbstractData {
	
	private int rewardType;
	public GetVipRewardList(int sessionId, int serial) {
		super(Protocol.MAIN_VIP, Protocol.VIP_GetVipRewardList, sessionId, serial);
	}

	public GetVipRewardList() {
		super(Protocol.MAIN_VIP, Protocol.VIP_GetVipRewardList);
	}

	public int getRewardType() {
		return rewardType;
	}

	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
	}

 

}
