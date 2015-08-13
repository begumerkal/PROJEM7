package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class NoticeLeader extends AbstractData {
	private int num;
	
    public NoticeLeader(int sessionId, int serial) {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_NoticeLeader, sessionId, serial);
    }

    public NoticeLeader() {
        super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_NoticeLeader);
    }

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
