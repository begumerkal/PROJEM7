package com.wyd.empire.protocol.data.errorcode;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetList extends AbstractData {

	public GetList(int sessionId, int serial) {
		super(Protocol.MAIN_ERRORCODE, Protocol.ERRORCODE_GetList, sessionId, serial);
	}

	public GetList() {
		super(Protocol.MAIN_ERRORCODE, Protocol.ERRORCODE_GetList);
	}
}
