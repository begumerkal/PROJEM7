package com.wyd.empire.protocol.data.cache;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

/**
 * 更改商品状态
 * @author zengxc
 *
 */
public class ChangeShop extends AbstractData {
	private int id;// 商品序号
	private boolean isOnSale;
	public ChangeShop(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_ChangeShop, sessionId, serial);
	}

	public ChangeShop() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_ChangeShop);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean getIsOnSale() {
		return isOnSale;
	}
	public void setIsOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}
}
