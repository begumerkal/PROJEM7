package com.wyd.empire.world.battle;

public class MonthCardVo {
	private int cardId; // 月卡id
	private String monthCardName; // 月卡名
	private int purchaseAmount; // 月卡购买所需金额
	private int dailyRebate; // 每日返还钻石数

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getMonthCardName() {
		return monthCardName;
	}

	public void setMonthCardName(String monthCardName) {
		this.monthCardName = monthCardName;
	}

	public int getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(int purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public int getDailyRebate() {
		return dailyRebate;
	}

	public void setDailyRebate(int dailyRebate) {
		this.dailyRebate = dailyRebate;
	}

}
