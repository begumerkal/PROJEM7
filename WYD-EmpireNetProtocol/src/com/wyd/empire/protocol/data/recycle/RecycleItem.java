package com.wyd.empire.protocol.data.recycle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class RecycleItem extends AbstractData {
	
	private	int[]	itemId;		//回收物品列表
	private	int[]	itemNum;		//物品数量

    public RecycleItem(int sessionId, int serial) {
        super(Protocol.MAIN_RECYCLE, Protocol.RECYCLE_RecycleItem, sessionId, serial);
    }

    public RecycleItem() {
        super(Protocol.MAIN_RECYCLE, Protocol.RECYCLE_RecycleItem);
    }

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

	public int[] getItemNum() {
		return itemNum;
	}

	public void setItemNum(int[] itemNum) {
		this.itemNum = itemNum;
	}
    
}
