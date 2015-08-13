package com.app.empire.protocol.data.task;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetOnileRewardListOk extends AbstractData {
    private int   onlineTime;  // 玩家在线多少秒后可以领取在线奖励
    private int[] onlineItem;  // 在线奖励的物品id
    private int[] onlineCount; // 在线奖励的物品数量
    private int   lotteryTime; // 玩家在线多少秒后可以获得抽奖机会
    private int[] lotteryItem; // 抽奖奖励的物品id
    private int   lotteryTimes;// 玩家可抽奖次数

    public GetOnileRewardListOk(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetOnileRewardListOk, sessionId, serial);
    }

    public GetOnileRewardListOk() {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetOnileRewardListOk);
    }

    public int getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(int onlineTime) {
        this.onlineTime = onlineTime;
    }

    public int[] getOnlineItem() {
        return onlineItem;
    }

    public void setOnlineItem(int[] onlineItem) {
        this.onlineItem = onlineItem;
    }

    public int[] getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int[] onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(int lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

    public int[] getLotteryItem() {
        return lotteryItem;
    }

    public void setLotteryItem(int[] lotteryItem) {
        this.lotteryItem = lotteryItem;
    }

    public int getLotteryTimes() {
        return lotteryTimes;
    }

    public void setLotteryTimes(int lotteryTimes) {
        this.lotteryTimes = lotteryTimes;
    }
}
