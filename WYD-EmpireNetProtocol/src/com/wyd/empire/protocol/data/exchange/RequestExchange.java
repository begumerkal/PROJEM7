package com.wyd.empire.protocol.data.exchange;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zengxc
 */
public class RequestExchange extends AbstractData {
	
	private	int	itemId;  //物品Id


    public RequestExchange(int sessionId, int serial) {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_RequestExchange, sessionId, serial);
    }

    public RequestExchange() {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_RequestExchange);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	

}
