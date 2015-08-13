package com.app.empire.protocol.data.player;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ChangeSkill extends AbstractData {
	private int toolId;
	private int index;

	public ChangeSkill(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_ChangeSkill, sessionId, serial);
	}

	public ChangeSkill() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_ChangeSkill);
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
