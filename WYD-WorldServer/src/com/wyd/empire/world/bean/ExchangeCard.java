package com.wyd.empire.world.bean;

/**
 * 用于兑换的卡牌
 * 
 * @author zengxc
 *
 */
public class ExchangeCard {
	private int cardId;// 卡牌ID
	private int price; // 兑换价格
	private int useNum;// 已使用次
	private int maxNum;// 最大次数

	/**
	 * 
	 * @param cardId
	 *            卡牌ID
	 * @param maxNum
	 *            最大兑换次数
	 */
	public ExchangeCard(int cardId, int price, int maxNum) {
		this.useNum = 0;
		this.maxNum = maxNum;
		this.cardId = cardId;
		this.price = price;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public int getUseNum() {
		return useNum;
	}

	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public boolean canExchange() {
		return useNum < maxNum;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void addUseNum(int add) {
		useNum += add;
	}
}
