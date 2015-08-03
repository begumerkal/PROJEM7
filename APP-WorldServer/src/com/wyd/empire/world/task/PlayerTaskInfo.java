package com.wyd.empire.world.task;

/**
 * 玩家任务完成情况（主要GM工具使用）
 * 
 * @author sunzx
 *
 */
public class PlayerTaskInfo extends PlayerTask {
	/**
	 * 主线和活跃度任务
	 * 
	 * @param taskId
	 * @param taskType
	 *            0：主线任务 ,1：活跃度任务
	 * @param targetValue
	 */
	public PlayerTaskInfo(int taskId, byte taskType, int[] targetValue) {
		super(taskId, taskType, targetValue);
	}

	/**
	 * 日常任务
	 * 
	 * @param taskId
	 * @param taskSubType
	 * @param target
	 * @param targetValue
	 */
	public PlayerTaskInfo(int taskId, byte taskSubType, byte targetType, int[] targetValue) {
		super(taskId, taskSubType, targetType, targetValue);
	}

	private static final long serialVersionUID = 1L;
	private String taskName;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
