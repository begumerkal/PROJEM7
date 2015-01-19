package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class LockSkill extends AbstractData {
	
	private int itemId;
	private int skilLId;
	
    public LockSkill(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_LockSkill, sessionId, serial);
    }

    public LockSkill() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_LockSkill);
    }


	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getSkilLId() {
		return skilLId;
	}

	public void setSkilLId(int skilLId) {
		this.skilLId = skilLId;
	}
}
