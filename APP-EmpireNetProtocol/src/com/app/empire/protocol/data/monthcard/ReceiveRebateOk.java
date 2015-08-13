package com.app.empire.protocol.data.monthcard;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 领取今日返利成功
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class ReceiveRebateOk extends AbstractData {
	private int  receiveRebateNumber;		//成功领取返利钻石数


	
    public ReceiveRebateOk(int sessionId, int serial) {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_ReceiveRebateOk, sessionId, serial);
    }

    public ReceiveRebateOk() {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_ReceiveRebateOk);
    }

	public int getReceiveRebateNumber() {
		return receiveRebateNumber;
	}

	public void setReceiveRebateNumber(int receiveRebateNumber) {
		this.receiveRebateNumber = receiveRebateNumber;
	}



    

}
