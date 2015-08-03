package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SendTaskList extends AbstractData {
    private int      taskStar;    // 玩家任务星级
    private int      taskTopLevel; // 任务最高提升等级
    private int[]    taskId;      // 任务id
    private byte[]   taskType;    // 任务类型0：主线任务 ,1：活跃度任务,2日常任务
    private byte[]   taskSubType; // 任务子类型
    private byte[]   taskStatus;  // 任务状态 0未触发，1进行中，2已完成，3已领奖
    private int[]    targetCount;  // 任务条件数量
    private byte[]   targetType;  // 任务条件类型
    private int[]    targetStatus; // 任务完成状态
    private int[]    targetValue; // 任务条件需求
    private String[] parenthesis; // 任务附加参数
    private int[]    diamond;     // 直接完成任务所需钻石数量
    private int[]    upLevel;     // 当前任务奖励提升等级
    private int[]    itemCount;   // 任务奖励的物品数量
    private int[]    itemId;      // 任务奖励物品id
    private int[]    expend;      // 任务提升级别所需钻石数量
    private int[]    upCount;     // 任务提升级别后奖励物品数量

	public SendTaskList(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_SendTaskList, sessionId, serial);
	}

	public SendTaskList() {
		super(Protocol.MAIN_TASK, Protocol.TASK_SendTaskList);
	}

    public int getTaskStar() {
        return taskStar;
    }

    public void setTaskStar(int taskStar) {
        this.taskStar = taskStar;
    }

    public int getTaskTopLevel() {
        return taskTopLevel;
    }

    public void setTaskTopLevel(int taskTopLevel) {
        this.taskTopLevel = taskTopLevel;
    }

    public int[] getTaskId() {
        return taskId;
    }

    public void setTaskId(int[] taskId) {
        this.taskId = taskId;
    }

    public byte[] getTaskType() {
        return taskType;
    }

    public void setTaskType(byte[] taskType) {
        this.taskType = taskType;
    }

    public byte[] getTaskSubType() {
        return taskSubType;
    }

    public void setTaskSubType(byte[] taskSubType) {
        this.taskSubType = taskSubType;
    }

    public byte[] getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(byte[] taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int[] getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(int[] targetCount) {
        this.targetCount = targetCount;
    }

    public byte[] getTargetType() {
        return targetType;
    }

    public void setTargetType(byte[] targetType) {
        this.targetType = targetType;
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

    public String[] getParenthesis() {
        return parenthesis;
    }

    public void setParenthesis(String[] parenthesis) {
        this.parenthesis = parenthesis;
    }

    public int[] getDiamond() {
        return diamond;
    }

    public void setDiamond(int[] diamond) {
        this.diamond = diamond;
    }

    public int[] getUpLevel() {
        return upLevel;
    }

    public void setUpLevel(int[] upLevel) {
        this.upLevel = upLevel;
    }

    public int[] getItemCount() {
        return itemCount;
    }

    public void setItemCount(int[] itemCount) {
        this.itemCount = itemCount;
    }

    public int[] getItemId() {
        return itemId;
    }

    public void setItemId(int[] itemId) {
        this.itemId = itemId;
    }

    public int[] getExpend() {
        return expend;
    }

    public void setExpend(int[] expend) {
        this.expend = expend;
    }

    public int[] getUpCount() {
        return upCount;
    }

    public void setUpCount(int[] upCount) {
        this.upCount = upCount;
    }
}
