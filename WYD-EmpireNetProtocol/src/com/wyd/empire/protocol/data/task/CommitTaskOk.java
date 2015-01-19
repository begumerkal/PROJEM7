package com.wyd.empire.protocol.data.task;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class CommitTaskOk extends AbstractData {
    private int   taskId;
    private byte taskType;

    public CommitTaskOk(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_CommitTaskOk, sessionId, serial);
    }

    public CommitTaskOk() {
        super(Protocol.MAIN_TASK, Protocol.TASK_CommitTaskOk);
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
}
