package com.app.empire.protocol.data.vip;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ReceiveGiftBagOk extends AbstractData {

	private int[] itemid;	
	private int[] num;
	
    public ReceiveGiftBagOk(int sessionId, int serial) {
        super(Protocol.MAIN_VIP, Protocol.VIP_ReceiveGiftBagOk, sessionId, serial);
    }

    public ReceiveGiftBagOk() {
        super(Protocol.MAIN_VIP, Protocol.VIP_ReceiveGiftBagOk);
    }

	public int[] getItemid() {
		return itemid;
	}

	public void setItemid(int[] itemid) {
		this.itemid = itemid;
	}

	public int[] getNum() {
		return num;
	}

	public void setNum(int[] num) {
		this.num = num;
	}

 

	
}
