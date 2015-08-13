package com.app.empire.protocol.data.task;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class SendEverydayRewardList extends AbstractData {
    private int       loginDays;  // 玩家当前累积登录天数
    private int[]     types;      // 奖励类型 2累积登录奖励，3登录目标奖励
    private int[]     days;       // 累计登录天数
    private boolean[] reward;     // 累计登录天数
    private int[]     rewardCount; // 奖励物品数量
    private int[]     itemIds;    // 奖励物品id
    private int[]     itemCount;  // 奖励物品数量

    public SendEverydayRewardList(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_SendEverydayRewardList, sessionId, serial);
    }

    public SendEverydayRewardList() {
        super(Protocol.MAIN_TASK, Protocol.TASK_SendEverydayRewardList);
    }

    public int getLoginDays() {
        return loginDays;
    }

    public void setLoginDays(int loginDays) {
        this.loginDays = loginDays;
    }

    public int[] getTypes() {
        return types;
    }

    public void setTypes(int[] types) {
        this.types = types;
    }

    public int[] getDays() {
        return days;
    }

    public void setDays(int[] days) {
        this.days = days;
    }

    public boolean[] getReward() {
        return reward;
    }

    public void setReward(boolean[] reward) {
        this.reward = reward;
    }

    public int[] getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(int[] rewardCount) {
        this.rewardCount = rewardCount;
    }

    public int[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(int[] itemIds) {
        this.itemIds = itemIds;
    }

    public int[] getItemCount() {
        return itemCount;
    }

    public void setItemCount(int[] itemCount) {
        this.itemCount = itemCount;
    }
}
