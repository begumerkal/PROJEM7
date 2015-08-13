package com.app.empire.protocol.data.cache;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UpdatePlayer extends AbstractData {
	private String[] key;
	private String[] value;
	
	public UpdatePlayer(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_UpdatePlayer, sessionId, serial);
	}

	public UpdatePlayer() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_UpdatePlayer);
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
