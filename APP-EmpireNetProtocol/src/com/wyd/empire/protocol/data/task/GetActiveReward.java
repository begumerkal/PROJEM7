package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetActiveReward extends AbstractData {
    private int rewardIndex;
	public GetActiveReward(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetActiveReward, sessionId, serial);
	}

	public GetActiveReward() {
		super(Protocol.MAIN_TASK, Protocol.TASK_GetActiveReward);
	}

    public int getRewardIndex() {
        return rewardIndex;
    }

    public void setRewardIndex(int rewardIndex) {
        this.rewardIndex = rewardIndex;
    }
}
