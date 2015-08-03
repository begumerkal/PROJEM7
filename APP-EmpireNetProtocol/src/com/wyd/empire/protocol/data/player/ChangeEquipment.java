package com.wyd.empire.protocol.data.player;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

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
