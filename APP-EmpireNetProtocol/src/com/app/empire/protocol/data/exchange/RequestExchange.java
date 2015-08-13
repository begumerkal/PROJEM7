package com.app.empire.protocol.data.exchange;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
