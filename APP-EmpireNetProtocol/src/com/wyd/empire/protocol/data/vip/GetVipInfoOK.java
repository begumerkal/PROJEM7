package com.wyd.empire.protocol.data.vip;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
	
 */
public class GetVipInfoOK extends AbstractData {

    private int vipExp; //vip经验
    private int vipLv;  //vip等级
    private int nextLvExp; //升级到下个等级需要经验
    private boolean isReceiveDayPack;//是否领取了vip每日礼包
    
    
    
    public GetVipInfoOK(int sessionId, int serial) {
        super(Protocol.MAIN_VIP, Protocol.VIP_GetVipInfoOK, sessionId, serial);
    }

    public GetVipInfoOK() {
        super(Protocol.MAIN_VIP, Protocol.VIP_GetVipInfoOK);
    }

	public int getVipExp() {
		return vipExp;
	}

	public void setVipExp(int vipExp) {
		this.vipExp = vipExp;
	}

	public int getVipLv() {
		return vipLv;
	}

	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}

	public int getNextLvExp() {
		return nextLvExp;
	}

	public void setNextLvExp(int nextLvExp) {
		this.nextLvExp = nextLvExp;
	}

	public boolean getIsReceiveDayPack() {
		return isReceiveDayPack;
	}

	public void setIsReceiveDayPack(boolean isReceiveDayPack) {
		this.isReceiveDayPack = isReceiveDayPack;
	}


 
}
