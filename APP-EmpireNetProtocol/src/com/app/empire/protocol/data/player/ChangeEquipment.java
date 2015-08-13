package com.app.empire.protocol.data.player;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ChangeEquipment extends AbstractData {
	private int newItemId;		//新物品ID
	public ChangeEquipment(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_ChangeEquipment, sessionId, serial);
	}

	public ChangeEquipment() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_ChangeEquipment);
	}


	public int getNewItemId() {
		return newItemId;
	}

	public void setNewItemId(int newItemId) {
		this.newItemId = newItemId;
	}
	
}
