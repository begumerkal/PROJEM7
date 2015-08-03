package com.wyd.empire.protocol.data.bossmaproom;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class MakePairFail extends AbstractData {

	public MakePairFail(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_MakePairFail, sessionId, serial);
	}

	public MakePairFail() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_MakePairFail);
	}
}
