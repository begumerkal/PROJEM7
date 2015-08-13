package com.app.empire.protocol.data.monthcard;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 扣费成功服务器主动下发协议
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class CashProductBuySuccess extends AbstractData {
	private	int		cardId;			//购买到物品id
	private	String	remark;		//备注信息【获得天数,每日返利钻石数】
	
    public CashProductBuySuccess(int sessionId, int serial) {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_CashProductBuySuccess, sessionId, serial);
    }

    public CashProductBuySuccess() {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_CashProductBuySuccess);
    }


	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    
    

}
