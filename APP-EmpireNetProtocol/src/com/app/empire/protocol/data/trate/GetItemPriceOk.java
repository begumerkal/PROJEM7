package com.app.empire.protocol.data.trate;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetItemPriceOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_TRATE下子命令TRATE_GetItemPriceOk(返回物品列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetItemPriceOk extends AbstractData {
	
	private int		priceCount;	//价格数量
	private int[]	id;	//价格序号
	private int[]	shopItemId;	//对应的商品id
	private int[]	days;	//可用的天数
	private int[]	count;	//可用的数量(-1不限次数）
	private byte[]	costType;	//0：用点劵 1:用金币 2：两种都用 -1：免费 3：勋章
	private int[]	costUseTickets;	//需要花费的点劵
	private int[]	costUseGold;	//需要花费的金币
	private int[]	costUseBadge;	//需要花费的兑换点券
	private int[]	costUseTicketsPrev;	//需要花费的点劵（打折前）
	private int[]	costUseGoldPrev;	//需要花费的金币（打折前）
	private int[]	costUseBadgePrev;	//需花费的勋章数（打折前）


	

    public GetItemPriceOk(int sessionId, int serial) {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetItemPriceOk, sessionId, serial);
    }

    public GetItemPriceOk() {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetItemPriceOk);
    }

	public int getPriceCount() {
		return priceCount;
	}

	public void setPriceCount(int priceCount) {
		this.priceCount = priceCount;
	}

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}

	public int[] getShopItemId() {
		return shopItemId;
	}

	public void setShopItemId(int[] shopItemId) {
		this.shopItemId = shopItemId;
	}

	public int[] getDays() {
		return days;
	}

	public void setDays(int[] days) {
		this.days = days;
	}

	public int[] getCount() {
		return count;
	}

	public void setCount(int[] count) {
		this.count = count;
	}

	public byte[] getCostType() {
		return costType;
	}

	public void setCostType(byte[] costType) {
		this.costType = costType;
	}

	public int[] getCostUseTickets() {
		return costUseTickets;
	}

	public void setCostUseTickets(int[] costUseTickets) {
		this.costUseTickets = costUseTickets;
	}

	public int[] getCostUseGold() {
		return costUseGold;
	}

	public void setCostUseGold(int[] costUseGold) {
		this.costUseGold = costUseGold;
	}

	public int[] getCostUseBadge() {
		return costUseBadge;
	}

	public void setCostUseBadge(int[] costUseBadge) {
		this.costUseBadge = costUseBadge;
	}

	public int[] getCostUseTicketsPrev() {
		return costUseTicketsPrev;
	}

	public void setCostUseTicketsPrev(int[] costUseTicketsPrev) {
		this.costUseTicketsPrev = costUseTicketsPrev;
	}

	public int[] getCostUseGoldPrev() {
		return costUseGoldPrev;
	}

	public void setCostUseGoldPrev(int[] costUseGoldPrev) {
		this.costUseGoldPrev = costUseGoldPrev;
	}

	public int[] getCostUseBadgePrev() {
		return costUseBadgePrev;
	}

	public void setCostUseBadgePrev(int[] costUseBadgePrev) {
		this.costUseBadgePrev = costUseBadgePrev;
	}

}
