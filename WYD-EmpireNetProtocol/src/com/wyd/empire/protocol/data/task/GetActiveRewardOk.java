package com.wyd.empire.protocol.data.task;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetActiveRewardOk extends AbstractData {
    private int[] rewardItems; // 奖励物品
    private int[] rewardCount; // 奖励数量

    public GetActiveRewardOk(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetActiveRewardOk, sessionId, serial);
    }

    public GetActiveRewardOk() {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetActiveRewardOk);
    }

    public int[] getRewardItems() {
        return rewardItems;
    }

    public void setRewardItems(int[] rewardItems) {
        this.rewardItems = rewardItems;
    }

    public int[] getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(int[] rewardCount) {
        this.rewardCount = rewardCount;
    }
}
