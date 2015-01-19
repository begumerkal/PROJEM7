package com.wyd.empire.protocol.data.task;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class TiroFlish extends AbstractData {
    private int id;
	

	public TiroFlish(int sessionId, int serial) {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroFlish, sessionId, serial);
	}

	public TiroFlish() {
		super(Protocol.MAIN_TASK, Protocol.TASK_TiroFlish);
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
