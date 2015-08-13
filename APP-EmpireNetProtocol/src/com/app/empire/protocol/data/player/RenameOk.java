package com.app.empire.protocol.data.player;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class RenameOk extends AbstractData {
	private String newName;

	public RenameOk(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_RenameOk, sessionId, serial);
	}

	public RenameOk() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_RenameOk);
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

}
