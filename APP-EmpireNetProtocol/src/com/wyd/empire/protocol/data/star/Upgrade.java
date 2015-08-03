package com.wyd.empire.protocol.data.star;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Upgrade extends AbstractData {
	private int itemId;
	private int[] stoneId;//升级石

    public Upgrade(int sessionId, int serial) {
        super(Protocol.MAIN_STAR, Protocol.STAR_Upgrade, sessionId, serial);
    }

    public Upgrade() {
        super(Protocol.MAIN_STAR, Protocol.STAR_Upgrade);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int[] getStoneId() {
		return stoneId;
	}

	public void setStoneId(int[] stoneId) {
		this.stoneId = stoneId;
	}

	

}
