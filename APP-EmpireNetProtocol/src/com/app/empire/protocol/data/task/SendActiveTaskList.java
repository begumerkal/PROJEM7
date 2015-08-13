package com.app.empire.protocol.data.task;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class SendActiveTaskList extends AbstractData {
    private int[]     taskId;        // 任务id
    private byte[]    taskStatus;    // 任务状态 0未触发，1进行中，2已完成，3已领奖
    private int[]     targetStatus;  // 任务完成状态
    private int[]     targetValue;   // 任务条件需求
    private int[]     hyz;           // 任务奖励活跃值
    private int       activity;
    private int[]     activityDemand; // 领取奖励活跃度要求值
    private boolean[] isReward;      // 该奖励是否已领取
    private int[]     rewardCount;   // 礼包奖励的物品数量
    private int[]     itemIds;       // 奖励物品id
    private int[]     itemCount;     // 奖励物品数量

    public SendActiveTaskList(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_SendActiveTaskList, sessionId, serial);
    }

    public SendActiveTaskList() {
        super(Protocol.MAIN_TASK, Protocol.TASK_SendActiveTaskList);
    }

    public int[] getTaskId() {
        return taskId;
    }

    public void setTaskId(int[] taskId) {
        this.taskId = taskId;
    }

    public byte[] getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(byte[] taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int[] getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(int[] targetStatus) {
        this.targetStatus = targetStatus;
    }

    public int[] getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(int[] targetValue) {
        this.targetValue = targetValue;
    }

    public int[] getHyz() {
        return hyz;
    }

    public void setHyz(int[] hyz) {
        this.hyz = hyz;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int[] getActivityDemand() {
        return activityDemand;
    }

    public void setActivityDemand(int[] activityDemand) {
        this.activityDemand = activityDemand;
    }

    public boolean[] getIsReward() {
        return isReward;
    }

    public void setIsReward(boolean[] isReward) {
        this.isReward = isReward;
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
