package com.app.empire.protocol.data.room;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class QuitRoomOk extends AbstractData {
    private boolean mark;//是否被踢
	public QuitRoomOk(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_QuitRoomOk, sessionId, serial);
	}

	public QuitRoomOk() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_QuitRoomOk);
	}

    public boolean getMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
