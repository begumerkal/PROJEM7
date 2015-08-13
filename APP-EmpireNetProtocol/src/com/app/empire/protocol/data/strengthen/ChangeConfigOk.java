package com.app.empire.protocol.data.strengthen;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class ChangeConfigOk extends AbstractData {
	private int diamondToGold;//钻石转成金币的倍数（万分比）
	private int discount;//折扣(万分比）
    public ChangeConfigOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_ChangeConfigOk, sessionId, serial);
    }
    public ChangeConfigOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_ChangeConfigOk);
    }

	public int getDiamondToGold() {
		return diamondToGold;
	}

	public void setDiamondToGold(int diamondToGold) {
		this.diamondToGold = diamondToGold;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

   
}
