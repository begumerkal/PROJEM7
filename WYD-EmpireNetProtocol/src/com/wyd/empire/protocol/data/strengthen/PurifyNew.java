package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class PurifyNew extends AbstractData {
    
    /** 武器ID*/
    private int wapenId;
    
    /** 物品列表ID*/
    private int [] itemId;
    
    /**锁定技能ID*/
    private int lockId;
    
    public int getLockId() {
		return lockId;
	}

	public void setLockId(int lockId) {
		this.lockId = lockId;
	}

	public PurifyNew(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_PurifyNew, sessionId, serial);
    }

    public PurifyNew() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_PurifyNew);
    }

    public int [] getItemId() {
        return itemId;
    }

    public void setItemId(int [] itemId) {
        this.itemId = itemId;
    }

    public int getWapenId() {
        return wapenId;
    }

    public void setWapenId(int wapenId) {
        this.wapenId = wapenId;
    }
}
