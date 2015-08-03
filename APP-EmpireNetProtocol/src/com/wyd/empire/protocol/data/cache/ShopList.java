package com.wyd.empire.protocol.data.cache;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ShopList extends AbstractData {
	private int[] id;// 物品序号
	private byte[] maintype;// 主类型
	private byte[] subtype;// 主类型是其它类型可以使用这个再分类
	private boolean[] isOnSale;// 是否上架中
	private int[] floorPrice;// 底价
	private int[] payType;// 支付类型 0：点卷 1：金币 2：勋章兑换
	private boolean[] newMark;// 是否是新品（true：是新品，false：不是新品）
	private int[] discount;// 商品折扣（%） 无折扣为100 有折扣时如：90
	private boolean[] recommended;// true为推荐物品

	public ShopList(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_ShopList, sessionId, serial);
	}

	public ShopList() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_ShopList);
	}

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}

	public byte[] getMaintype() {
		return maintype;
	}

	public void setMaintype(byte[] maintype) {
		this.maintype = maintype;
	}

	public byte[] getSubtype() {
		return subtype;
	}

	public void setSubtype(byte[] subtype) {
		this.subtype = subtype;
	}

	public boolean[] getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(boolean[] isOnSale) {
		this.isOnSale = isOnSale;
	}

	public int[] getFloorPrice() {
		return floorPrice;
	}

	public void setFloorPrice(int[] floorPrice) {
		this.floorPrice = floorPrice;
	}

	public int[] getPayType() {
		return payType;
	}

	public void setPayType(int[] payType) {
		this.payType = payType;
	}


	public boolean[] getNewMark() {
		return newMark;
	}

	public void setNewMark(boolean[] newMark) {
		this.newMark = newMark;
	}


	public int[] getDiscount() {
		return discount;
	}

	public void setDiscount(int[] discount) {
		this.discount = discount;
	}

	public boolean[] getRecommended() {
		return recommended;
	}

	public void setRecommended(boolean[] recommended) {
		this.recommended = recommended;
	}
}
