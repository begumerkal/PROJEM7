package com.wyd.empire.protocol.data.cache;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ZflhList extends AbstractData {
	private int[] id;        // 物品序号
	private String[] itemId; // 物品ID 格式[865,866,867,868]
	private String[] itemNum;// 物品数量 格式[1,2,2,5]
	private int[] state;     // 0不可领取，1可以领取，2已领取
	

	public ZflhList(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_ZflhList, sessionId, serial);
	}

	public ZflhList() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_ZflhList);
	}

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}

	public String[] getItemId() {
		return itemId;
	}

	public void setItemId(String[] itemId) {
		this.itemId = itemId;
	}

	public String[] getItemNum() {
		return itemNum;
	}

	public void setItemNum(String[] itemNum) {
		this.itemNum = itemNum;
	}

	public int[] getState() {
		return state;
	}

	public void setState(int[] state) {
		this.state = state;
	}

	
}
