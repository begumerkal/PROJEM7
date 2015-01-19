package com.wyd.empire.protocol.data.cache;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class RemoveItem extends AbstractData {
	private int[] playerItemId;
	private int[] itemId;	
	public RemoveItem(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_RemoveItem, sessionId, serial);
	}

	public RemoveItem() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_RemoveItem);
		
	}

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

	public int[] getPlayerItemId() {
		return playerItemId;
	}

	public void setPlayerItemId(int[] playerItemId) {
		this.playerItemId = playerItemId;
	}


}
