package com.wyd.empire.protocol.data.lottery;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ReceiveRewardOk extends AbstractData {
	private int gridId; // 选中的格子序号  
	private	int	itemId;	// 格子里的物品
	private int itemNum;    // 格子里的物品数量 


	
	public ReceiveRewardOk(int sessionId, int serial) {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveRewardOk, sessionId, serial);
	}

	public ReceiveRewardOk() {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveRewardOk);
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}	

	public int getGridId() {
		return gridId;
	}

	public void setGridId(int gridId) {
		this.gridId = gridId;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	

}
