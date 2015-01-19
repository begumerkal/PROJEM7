package com.wyd.empire.protocol.data.bossmaproom;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class QuitRoomOk extends AbstractData {
    private boolean mark;//是否被踢
	public QuitRoomOk(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_QuitRoomOk, sessionId, serial);
	}

	public QuitRoomOk() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_QuitRoomOk);
	}
	
    public boolean getMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
