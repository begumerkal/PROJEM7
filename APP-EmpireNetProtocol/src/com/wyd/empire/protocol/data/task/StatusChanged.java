package com.wyd.empire.protocol.data.task;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class StatusChanged extends AbstractData {
    private int   taskId;
    private byte  taskType;
    private int   status;
    private int[] targetStatus; // 任务完成状态

    public StatusChanged(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_StatusChanged, sessionId, serial);
    }

    public StatusChanged() {
        super(Protocol.MAIN_TASK, Protocol.TASK_StatusChanged);
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int[] getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(int[] targetStatus) {
        this.targetStatus = targetStatus;
    }
}
