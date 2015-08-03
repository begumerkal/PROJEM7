package com.wyd.empire.protocol.data.monthcard;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 获取月卡列表成功
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class GetMonthCardListOk extends AbstractData {
	private	int[]		cardIds;		//月卡id【客户端根据此id进行资源加载，服务端不提供资源名称】
//	private	int[]		purchaseAmount;	//月卡购买所需金额
	private	int[]		dailyRebate;	//每日返还钻石数
	private	boolean		isBuy;			//是否已经购买了月卡
	private	int			playerCard;		//玩家购买的月卡id
	private	boolean		isCanReceive;	//是否可以领取返利
	private	int			remainingDays;	//月卡剩余天数
	
    public GetMonthCardListOk(int sessionId, int serial) {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_GetMonthCardListOk, sessionId, serial);
    }

    public GetMonthCardListOk() {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_GetMonthCardListOk);
    }

	public int[] getCardIds() {
		return cardIds;
	}

	public void setCardIds(int[] cardIds) {
		this.cardIds = cardIds;
	}

//	public int[] getPurchaseAmount() {
//		return purchaseAmount;
//	}
//
//	public void setPurchaseAmount(int[] purchaseAmount) {
//		this.purchaseAmount = purchaseAmount;
//	}

	public int[] getDailyRebate() {
		return dailyRebate;
	}

	public void setDailyRebate(int[] dailyRebate) {
		this.dailyRebate = dailyRebate;
	}

	public boolean getIsBuy() {
		return isBuy;
	}

	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}

	public int getPlayerCard() {
		return playerCard;
	}

	public void setPlayerCard(int playerCard) {
		this.playerCard = playerCard;
	}

	public boolean getIsCanReceive() {
		return isCanReceive;
	}

	public void setCanReceive(boolean isCanReceive) {
		this.isCanReceive = isCanReceive;
	}

	public int getRemainingDays() {
		return remainingDays;
	}

	public void setRemainingDays(int remainingDays) {
		this.remainingDays = remainingDays;
	}

	
    
	
    

}
