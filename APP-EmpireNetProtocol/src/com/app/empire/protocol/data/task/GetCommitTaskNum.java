package com.app.empire.protocol.data.task;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetCommitTaskNum extends AbstractData {
    private int taskNum;

    public GetCommitTaskNum(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetCommitTaskNum, sessionId, serial);
    }

    public GetCommitTaskNum() {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetCommitTaskNum);
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }
}
