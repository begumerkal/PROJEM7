package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetVigorPriceOk extends AbstractData {
	private int price;//花费的钻石(-1无法购买－2钻石不够)
	private int vigor;//得到的活力值
	
	
	
	public GetVigorPriceOk(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetVigorPriceOk, sessionId, serial);
    }
	public GetVigorPriceOk(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetVigorPriceOk);
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getVigor() {
		return vigor;
	}
	public void setVigor(int vigor) {
		this.vigor = vigor;
	}
	


}
