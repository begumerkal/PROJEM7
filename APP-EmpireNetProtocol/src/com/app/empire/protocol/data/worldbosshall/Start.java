package com.app.empire.protocol.data.worldbosshall;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 开始战斗
 * @author zengxc
 *
 */
public class Start extends AbstractData {
	private int roomId; // 房间ID 
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public Start(int sessionId, int serial) {
        super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_Start, sessionId, serial);
    }
	public Start(){
		super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_Start);
	}

}
