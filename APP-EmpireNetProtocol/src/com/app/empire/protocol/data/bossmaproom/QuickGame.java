package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class QuickGame extends AbstractData {
	public QuickGame(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_QuickGame, sessionId, serial);
	}

	public QuickGame() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_QuickGame);
	}

}
