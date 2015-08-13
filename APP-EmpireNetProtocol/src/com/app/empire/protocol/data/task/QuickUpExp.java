package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class QuickUpExp extends AbstractData {
	
	private int taskId;

	public QuickUpExp(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_QuickUpExp, sessionId, serial);
	}

	public QuickUpExp() {
		super(Protocol.MAIN_TASK, Protocol.TASK_QuickUpExp);
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
}
