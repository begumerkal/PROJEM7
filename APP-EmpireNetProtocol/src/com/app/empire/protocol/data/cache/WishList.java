package com.app.empire.protocol.data.cache;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class WishList extends AbstractData {
	private int[] id;// 物品序号
	private int[] num;// 物品数量
	

	public WishList(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_WishList, sessionId, serial);
	}

	public WishList() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_WishList);
	}

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}

	public int[] getNum() {
		return num;
	}

	public void setNum(int[] num) {
		this.num = num;
	}
}
