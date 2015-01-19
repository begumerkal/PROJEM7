package com.wyd.empire.world.title;

import java.util.Date;


/**
 * 保存玩家与任务关系中间信息
 * @author sunzx
 */
public class PlayerTaskVo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int taskId;
	private byte taskType;// 0：主线任务 1：支线任务 2：日常任务 3：活动任务
	private byte taskSubType;// 主线任务：1，等级任务；2，其他任务。支线任务：1，普通支线任务。日常任务：1，普通日常任务；2，活跃度任务。活动任务：1，普通活动任务
	private byte status;// 任务状态 0未触发，1进行中，2已完成，3已领奖
	private Date endTime;// 任务完成时间
	private boolean isNew;// 是否新触发任务
	private String targetStatus; // 玩家的任务完成状态
	private int upLevel; //任务提升等级，默认没有提升，0级

	public PlayerTaskVo() {
	}

	public PlayerTaskVo(int taskId, byte taskType, byte taskSubType) {
		this.taskId = taskId;
		this.taskType = taskType;
		this.taskSubType = taskSubType;
		this.status = 0;
		this.isNew = true;
		this.upLevel = 0;
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
//		if (0 == taskSubType) {
//			Task task = ServiceManager.getManager().getTaskService().getTaskService().getTaskById(taskId);
//			if (null != task)
//				taskSubType = task.getTaskSubType();
//		}
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(String targetStatus) {
		this.targetStatus = targetStatus;
	}

	public int getUpLevel() {
		return upLevel;
	}

	public void setUpLevel(int upLevel) {
		this.upLevel = upLevel;
	}
}
