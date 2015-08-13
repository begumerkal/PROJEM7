package com.app.empire.protocol.data.task;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class SupplSignOk extends AbstractData {
    private int signDay;

    public SupplSignOk(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_SupplSignOk, sessionId, serial);
    }

    public SupplSignOk() {
        super(Protocol.MAIN_TASK, Protocol.TASK_SupplSignOk);
    }

    public int getSignDay() {
        return signDay;
    }

    public void setSignDay(int signDay) {
        this.signDay = signDay;
    }
}
