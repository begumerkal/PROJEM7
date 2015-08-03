package com.wyd.empire.protocol.data.worldbosshall;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 获取挑战大厅状态
 * @author zengxc
 *
 */
public class GetHallState extends AbstractData {
	private int mapId; //boss地图Id(-1表示默认地图)
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public GetHallState(int sessionId, int serial) {
        super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetHallState, sessionId, serial);
    }
	public GetHallState(){
		super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetHallState);
	}

}
