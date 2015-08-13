package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class Merge extends AbstractData {
    
    /** 武器ID*/
    private int wapenId;
    
    /** 物品列表ID*/
    private int[] itemId;
    
    /** 版本标识*/
    private int mark = 0;
    
    public Merge(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_Merge, sessionId, serial);
    }

    public Merge() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_Merge);
    }

    public int[] getItemId() {
        return itemId;
    }

    public void setItemId(int[] itemId) {
        this.itemId = itemId;
    }

    public int getWapenId() {
        return wapenId;
    }

    public void setWapenId(int wapenId) {
        this.wapenId = wapenId;
    }

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
    
}
