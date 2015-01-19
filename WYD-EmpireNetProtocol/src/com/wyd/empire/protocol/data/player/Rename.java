package com.wyd.empire.protocol.data.player;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Rename extends AbstractData {
	private String newName;

	public Rename(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_Rename, sessionId, serial);
	}

	public Rename() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_Rename);
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

}
