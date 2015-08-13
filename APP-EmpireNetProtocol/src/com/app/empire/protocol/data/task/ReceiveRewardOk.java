package com.app.empire.protocol.data.task;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class ReceiveRewardOk extends AbstractData {
    private int[] rewardItems; // 奖励物品
    private int[] rewardCount; // 奖励数量

    public ReceiveRewardOk(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_ReceiveRewardOk, sessionId, serial);
    }

    public ReceiveRewardOk() {
        super(Protocol.MAIN_TASK, Protocol.TASK_ReceiveRewardOk);
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
