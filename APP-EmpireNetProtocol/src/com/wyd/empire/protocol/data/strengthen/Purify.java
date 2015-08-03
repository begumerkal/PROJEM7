package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Purify extends AbstractData {
    
    /** 武器ID*/
    private int wapenId;
    
    /** 物品列表ID*/
    private int itemId;
    
    

	public Purify(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_Purify, sessionId, serial);
    }

    public Purify() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_Purify);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getWapenId() {
        return wapenId;
    }

    public void setWapenId(int wapenId) {
        this.wapenId = wapenId;
    }
}
