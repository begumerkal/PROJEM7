package com.app.empire.protocol.data.draw;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class DrawRefresh extends AbstractData {
	
	private int typeId;   //玩家选择的抽奖类型的物品ID
	
    public DrawRefresh(int sessionId, int serial) {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_DrawRefresh, sessionId, serial);
    }

    public DrawRefresh() {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_DrawRefresh);
    }

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
}
