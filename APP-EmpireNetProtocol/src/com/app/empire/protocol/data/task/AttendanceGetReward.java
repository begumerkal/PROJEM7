package com.app.empire.protocol.data.task;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class AttendanceGetReward extends AbstractData {
    private int totalDays;

    public AttendanceGetReward(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_AttendanceGetReward, sessionId, serial);
    }

    public AttendanceGetReward() {
        super(Protocol.MAIN_TASK, Protocol.TASK_AttendanceGetReward);
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }
}
