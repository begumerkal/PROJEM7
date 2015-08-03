package com.wyd.empire.protocol.data.player;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ChangeProp extends AbstractData {
	private int toolId;
	private int index;

	public ChangeProp(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_ChangeProp, sessionId, serial);
	}

	public ChangeProp() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_ChangeProp);
	}

	public int getToolId() {
		return toolId;
	}

	public void setToolId(int toolId) {
		this.toolId = toolId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
