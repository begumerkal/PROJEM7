package com.app.empire.protocol.data.draw;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SendItemList extends AbstractData {
	
	private 	int	price;			//抽奖单价
	private 	int	starNum;			//玩家已经获得的星星数
	private 	int	freeNum;			//玩家免费刷新的剩余次数
	private 	int	refreshCost;			//玩家刷新的钻石数
	private 	int[] itemId;           // 物品ID
	private 	int[] itemNum;			// 物品数量	
	private     String  miniIcon;           //小图标
	private     int     totalNum;			//代币消耗总数
	
    public SendItemList(int sessionId, int serial) {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_SendItemList, sessionId, serial);
    }

    public SendItemList() {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_SendItemList);
    }

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStarNum() {
		return starNum;
	}

	public void setStarNum(int starNum) {
		this.starNum = starNum;
	}

	public int getFreeNum() {
		return freeNum;
	}

	public void setFreeNum(int freeNum) {
		this.freeNum = freeNum;
	}

	public int getRefreshCost() {
		return refreshCost;
	}

	public void setRefreshCost(int refreshCost) {
		this.refreshCost = refreshCost;
	}

	

	public int[] getItemNum() {
		return itemNum;
	}

	public void setItemNum(int[] itemNum) {
		this.itemNum = itemNum;
	}

	

	public String getMiniIcon() {
		return miniIcon;
	}

	public void setMiniIcon(String miniIcon) {
		this.miniIcon = miniIcon;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

}
