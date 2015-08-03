package com.wyd.empire.protocol.data.worldbosshall;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 清除冷却时间
 * @author zengxc
 *
 */
public class Accelerate extends AbstractData {
	private int mapId;//
	
	public Accelerate(int sessionId, int serial) {
        super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_Accelerate, sessionId, serial);
    }
	public Accelerate(){
		super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_Accelerate);
	}
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
}
