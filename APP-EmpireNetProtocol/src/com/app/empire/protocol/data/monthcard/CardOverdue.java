package com.app.empire.protocol.data.monthcard;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 月卡过期提示服务器主动下发协议
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class CardOverdue extends AbstractData {
	private int cardId;		//过期到物品id

    public CardOverdue(int sessionId, int serial) {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_CardOverdue, sessionId, serial);
    }

    public CardOverdue() {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_CardOverdue);
    }

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	
}
