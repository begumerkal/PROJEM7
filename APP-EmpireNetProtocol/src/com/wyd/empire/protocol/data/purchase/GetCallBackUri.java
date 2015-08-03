package com.wyd.empire.protocol.data.purchase;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetCallBackUri extends AbstractData {
	public GetCallBackUri(int sessionId, int serial) {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_GetCallBackUri, sessionId, serial);
	}

	public GetCallBackUri() {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_GetCallBackUri);
	}
}
