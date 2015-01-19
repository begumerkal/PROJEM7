package com.wyd.empire.protocol.data.bossmaproom;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class QuickGame extends AbstractData {
	public QuickGame(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_QuickGame, sessionId, serial);
	}

	public QuickGame() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_QuickGame);
	}

}
