package com.wyd.empire.protocol.data.vip;

import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
 
 
 
public class VipReceiveInfo extends AbstractData {

	private int[] isReceiveLvPack;//是否领取了vip等级礼包信息 1 为以领取(一共10级)
	private int   isEveryDayPack;//	1为以领取0为未领取
	
    public VipReceiveInfo(int sessionId, int serial) {
        super(Protocol.MAIN_VIP, Protocol.VIP_VipReceiveInfo, sessionId, serial);
    }

    public VipReceiveInfo() {
        super(Protocol.MAIN_VIP, Protocol.VIP_VipReceiveInfo);
    }

	public int[] getIsReceiveLvPack() {
		return isReceiveLvPack;
	}

	public void setIsReceiveLvPack(int[] isReceiveLvPack) {
		this.isReceiveLvPack = isReceiveLvPack;
	}

	public int getIsEveryDayPack() {
		return isEveryDayPack;
	}

	public void setIsEveryDayPack(int isEveryDayPack) {
		this.isEveryDayPack = isEveryDayPack;
	}

 
}
