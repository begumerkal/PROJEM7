package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class TiroTaskOk extends AbstractData {
    private int[] ids;
    private int[] step;

	public TiroTaskOk(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroTaskOk, sessionId, serial);
	}

	public TiroTaskOk() {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroTaskOk);
	}

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public int[] getStep() {
        return step;
    }

    public void setStep(int[] step) {
        this.step = step;
    }
}
