package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 武器碎片合成
 */
public class MergeScrap extends AbstractData {
    
    /** 武器ID*/
    private int weaponId;
    
    /** 物品列表ID*/
    private int[] itemId;
    
    /** 版本标识*/
    private int mark = 0;
    
    public MergeScrap(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_MergeScrap, sessionId, serial);
    }

    public MergeScrap() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_MergeScrap);
    }

    public int[] getItemId() {
        return itemId;
    }

    public void setItemId(int[] itemId) {
        this.itemId = itemId;
    }

    

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getWeaponId() {
		return weaponId;
	}

	public void setWeaponId(int weaponId) {
		this.weaponId = weaponId;
	}
    
}
