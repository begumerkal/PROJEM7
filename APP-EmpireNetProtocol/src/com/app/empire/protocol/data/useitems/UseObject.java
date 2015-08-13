package com.app.empire.protocol.data.useitems;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UseObject extends AbstractData {
	
	private int itemId; //玩家物品id
	private int count;  //使用数量

    public UseObject(int sessionId, int serial) {
        super(Protocol.MAIN_USEITEMS, Protocol.USEITEMS_UseObject, sessionId, serial);
    }

    public UseObject() {
        super(Protocol.MAIN_USEITEMS, Protocol.USEITEMS_UseObject);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
    
}
