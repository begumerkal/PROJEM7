package com.wyd.empire.protocol.data.task;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class QuickUpExpOk extends AbstractData {
    private int taskId;

    public QuickUpExpOk(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_QuickUpExpOk, sessionId, serial);
    }

    public QuickUpExpOk() {
        super(Protocol.MAIN_TASK, Protocol.TASK_QuickUpExpOk);
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
