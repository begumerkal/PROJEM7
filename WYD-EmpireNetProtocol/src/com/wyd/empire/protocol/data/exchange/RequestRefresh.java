package com.wyd.empire.protocol.data.exchange;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class RequestRefresh extends AbstractData {
	
	private	int	payType;		//支付类型（0表示钻石，1表示勋章）
	private	int	payNum;		//支付代币个数



    public RequestRefresh(int sessionId, int serial) {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_RequestRefresh, sessionId, serial);
    }

    public RequestRefresh() {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_RequestRefresh);
    }

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getPayNum() {
		return payNum;
	}

	public void setPayNum(int payNum) {
		this.payNum = payNum;
	}


}
