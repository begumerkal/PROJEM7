package com.wyd.empire.protocol.data.worldbosshall;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 获取房间状态
 * @author zengxc
 *
 */
public class GetRoomState extends AbstractData {
	private int mapId;
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public GetRoomState(int sessionId, int serial) {
        super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetRoomState, sessionId, serial);
    }
	public GetRoomState(){
		super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetRoomState);
	}

}
