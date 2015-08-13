package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class Mosaic extends AbstractData {
	
	private int	itemId;	//物品id
	private int[]	changeStone;	//镶嵌石ID
	/** 版本标识*/
    private int mark = 0;

	
    public Mosaic(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_Mosaic, sessionId, serial);
    }

    public Mosaic() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_Mosaic);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int[] getChangeStone() {
		return changeStone;
	}

	public void setChangeStone(int[] changeStone) {
		this.changeStone = changeStone;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

}
