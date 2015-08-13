package com.app.empire.protocol.data.vip;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取vip等级奖励
 */
public class GetVipLevelAward extends AbstractData {
	private int vipLv;	
	
	
	public GetVipLevelAward(int sessionId, int serial) {
        super(Protocol.MAIN_VIP, Protocol.VIP_GetVipLevelAward, sessionId, serial);
    }

    public GetVipLevelAward() {
        super(Protocol.MAIN_VIP, Protocol.VIP_GetVipLevelAward);
    }

	public int getVipLv() {
		return vipLv;
	}

	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}
    

    
    
    
}
