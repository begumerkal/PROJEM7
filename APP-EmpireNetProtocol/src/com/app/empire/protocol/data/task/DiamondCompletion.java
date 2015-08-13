package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class DiamondCompletion extends AbstractData {
	private int taskId;// 任务ID

	public DiamondCompletion(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_DiamondCompletion, sessionId, serial);
	}

	public DiamondCompletion() {
		super(Protocol.MAIN_TASK, Protocol.TASK_DiamondCompletion);
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
}
