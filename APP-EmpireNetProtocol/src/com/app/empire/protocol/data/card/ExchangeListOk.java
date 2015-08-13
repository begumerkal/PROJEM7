package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ExchangeListOk extends AbstractData {
	private int[] id;    //	卡牌ID
	private int[] exchangePrice; //	兑换价格
	private int refreshPrice;    // 刷新所需钻石数
	private int lastTime;        // 距离下次刷新的秒数
	private int[] exchangeNum;     // 剩余兑换次数 
	private String[] groupAddition; //套卡属性（暴击,免爆,破防,免伤,生命,攻击,防御 ）。三张｜五张。如： 25,25,20,30,0,0,0|0,0,0,0,200,40,30
	private int [] itemNum;         // 一次兑换得到物品数量

	
	
	public ExchangeListOk(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_ExchangeListOk, sessionId, serial);
    }
	public ExchangeListOk(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_ExchangeListOk);
	}
	public int[] getId() {
		return id;
	}
	public void setId(int[] id) {
		this.id = id;
	}
	
	public int[] getExchangePrice() {
		return exchangePrice;
	}
	public void setExchangePrice(int[] exchangePrice) {
		this.exchangePrice = exchangePrice;
	}
	public int getRefreshPrice() {
		return refreshPrice;
	}
	public void setRefreshPrice(int refreshPrice) {
		this.refreshPrice = refreshPrice;
	}
	public int getLastTime() {
		return lastTime;
	}
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}
	public int[] getExchangeNum() {
		return exchangeNum;
	}
	public void setExchangeNum(int[] exchangeNum) {
		this.exchangeNum = exchangeNum;
	}
	public String[] getGroupAddition() {
		return groupAddition;
	}
	public void setGroupAddition(String[] groupAddition) {
		this.groupAddition = groupAddition;
	}
	public int[] getItemNum() {
		return itemNum;
	}
	public void setItemNum(int[] itemNum) {
		this.itemNum = itemNum;
	}
	
}
