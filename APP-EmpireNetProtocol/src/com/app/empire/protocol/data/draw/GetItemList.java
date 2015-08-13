package com.app.empire.protocol.data.draw;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetItemList extends AbstractData {
	
	private int typeId;   //玩家选择的抽奖类型的物品ID
	
    public GetItemList(int sessionId, int serial) {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_GetItemList, sessionId, serial);
    }

    public GetItemList() {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_GetItemList);
    }

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
    
}
