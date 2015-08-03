package com.wyd.empire.protocol.data.singlemap;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class StartRaids extends AbstractData {
	private int pointId;//小关卡ID
	private int times;//扫荡次数
	public StartRaids(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_StartRaids, sessionId, serial);
    }
	public StartRaids(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_StartRaids);
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	
}
