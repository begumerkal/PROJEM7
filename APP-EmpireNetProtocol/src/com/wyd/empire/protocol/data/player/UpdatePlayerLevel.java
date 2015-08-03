package com.wyd.empire.protocol.data.player;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class UpdatePlayerLevel extends AbstractData {

	private int level;

    public UpdatePlayerLevel() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_UpdatePlayerLevel);
    }
    
    public UpdatePlayerLevel(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_UpdatePlayerLevel, sessionId, serial);
    }

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
