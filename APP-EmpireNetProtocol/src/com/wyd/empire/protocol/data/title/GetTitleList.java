package com.wyd.empire.protocol.data.title;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetTitleList extends AbstractData {

	public GetTitleList(int sessionId, int serial) {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_GetTitleList, sessionId, serial);
	}

	public GetTitleList() {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_GetTitleList);
	}
}
