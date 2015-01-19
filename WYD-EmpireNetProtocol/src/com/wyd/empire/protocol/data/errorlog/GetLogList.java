package com.wyd.empire.protocol.data.errorlog;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetLogList extends AbstractData {

	public GetLogList(int sessionId, int serial) {
		super(Protocol.MAIN_ERRORLOG, Protocol.ERRORLOG_GetLogList, sessionId, serial);
	}

	public GetLogList() {
		super(Protocol.MAIN_ERRORLOG, Protocol.ERRORLOG_GetLogList);
	}
}
