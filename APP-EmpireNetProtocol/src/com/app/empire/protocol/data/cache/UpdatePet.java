package com.app.empire.protocol.data.cache;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UpdatePet extends AbstractData {
	private int id;  // petitem.id
	private String[] key;
	private String[] value;
	public UpdatePet(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_UpdatePet, sessionId, serial);
	}

	public UpdatePet() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_UpdatePet);
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
