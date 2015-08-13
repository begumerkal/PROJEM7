package com.app.empire.protocol.data.cache;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UpdateItem extends AbstractData {
	private int id;  // playeritemsfromshop.id
	private String[] key;
	private String[] value;
	public UpdateItem(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_UpdateItem, sessionId, serial);
	}

	public UpdateItem() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_UpdateItem);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getKey() {
		return key;
	}

	public void setKey(String[] key) {
		this.key = key;
	}

	public String[] getValue() {
		return value;
	}

	public void setValue(String[] value) {
		this.value = value;
	}
}
