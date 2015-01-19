package com.wyd.empire.protocol.data.errorcode;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class CheckList extends AbstractData {

	public CheckList(int sessionId, int serial) {
		super(Protocol.MAIN_ERRORCODE, Protocol.ERRORCODE_CheckList, sessionId, serial);
	}

	public CheckList() {
		super(Protocol.MAIN_ERRORCODE, Protocol.ERRORCODE_CheckList);
	}
}
