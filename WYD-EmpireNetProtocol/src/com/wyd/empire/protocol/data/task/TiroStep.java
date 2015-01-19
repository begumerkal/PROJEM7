package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class TiroStep extends AbstractData {
    private int id;
    private int step;
	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public TiroStep(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroStep, sessionId, serial);
	}

	public TiroStep() {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroStep);
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
