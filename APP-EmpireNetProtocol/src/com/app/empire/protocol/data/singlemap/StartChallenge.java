package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class StartChallenge extends AbstractData {
	private int pointId;//小关卡ID
	public StartChallenge(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_StartChallenge, sessionId, serial);
    }
	public StartChallenge(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_StartChallenge);
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}

}
