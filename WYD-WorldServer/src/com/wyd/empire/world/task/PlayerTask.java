package com.wyd.empire.world.task;

import com.wyd.empire.world.common.util.Common;

/**
 * 保存玩家与任务关系中间信息
 * 
 * @author zgq
 */
public class PlayerTask implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int taskId;
	private byte taskType; // 0：主线任务 ,1：活跃度任务,2日常任务
	private byte status; // 任务状态 0未触发，1进行中，2已完成，3已领奖
	private long endTime; // 任务完成时间
	private int[] targetValue; // 玩家的任务完成目标值
	private int[] targetStatus; // 玩家的任务目标完成状态
	private byte taskSubType; // 任务子类型
	private byte targetType; // 条件类型
	private int upLevel; // 任务提升等级，默认没有提升，0级
	private String parenthesis; // 任务附加参数

	/**
	 * 主线和活跃度任务
	 * 
	 * @param taskId
	 * @param taskType
	 *            0：主线任务 ,1：活跃度任务
	 * @param targetValue
	 */
	public PlayerTask(int taskId, byte taskType, int[] targetValue) {
		this.taskId = taskId;
		this.taskType = taskType;
		this.status = Common.TASK_STATUS_UNTRIGGERED;
		this.upLevel = 0;
		this.targetValue = targetValue;
		this.targetStatus = new int[targetValue.length];
	}

	/**
	 * 日常任务
	 * 
	 * @param taskId
	 * @param taskSubType
	 * @param target
	 * @param targetValue
	 */
	public PlayerTask(int taskId, byte taskSubType, byte targetType, int[] targetValue) {
		this.taskId = taskId;
		this.taskType = 2;
		this.taskSubType = taskSubType;
		this.status = Common.TASK_STATUS_UNTRIGGERED;
		this.targetType = targetType;
		this.upLevel = 0;
		this.targetValue = targetValue;
		this.targetStatus = new int[targetValue.length];
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public byte getTaskType() {
		return taskType;
	}

	public void setTaskType(byte taskType) {
		this.taskType = taskType;
	}

	public byte getTaskSubType() {
		return taskSubType;
	}

	public void setTaskSubType(byte taskSubType) {
		this.taskSubType = taskSubType;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int[] getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(int[] targetValue) {
		this.targetValue = targetValue;
	}

	public int[] getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(int[] targetStatus) {
		this.targetStatus = targetStatus;
	}

	public byte getTargetType() {
		return targetType;
	}

	public void setTargetType(byte targetType) {
		this.targetType = targetType;
	}

	public int getUpLevel() {
		return upLevel;
	}

	public void setUpLevel(int upLevel) {
		this.upLevel = upLevel;
	}

	public String getParenthesis() {
		return parenthesis == null ? "" : parenthesis;
	}

	public void setParenthesis(String parenthesis) {
		this.parenthesis = parenthesis;
	}
}
