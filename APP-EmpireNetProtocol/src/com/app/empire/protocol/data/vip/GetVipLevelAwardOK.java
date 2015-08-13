package com.app.empire.protocol.data.vip;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetVipLevelAwardOK extends AbstractData{
	private int[] itemId;
	private int[] num;
	
    public GetVipLevelAwardOK(int sessionId, int serial) {
        super(Protocol.MAIN_VIP, Protocol.VIP_GetVipLevelAwardOK, sessionId, serial);
    }

    public GetVipLevelAwardOK() {
        super(Protocol.MAIN_VIP, Protocol.VIP_GetVipLevelAwardOK);
    }

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

	public int[] getNum() {
		return num;
	}

	public void setNum(int[] num) {
		this.num = num;
	}
 

    
}
