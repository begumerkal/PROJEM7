package com.app.empire.protocol.data.title;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetTitleList extends AbstractData {

	public GetTitleList(int sessionId, int serial) {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_GetTitleList, sessionId, serial);
	}

	public GetTitleList() {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_GetTitleList);
	}
}
