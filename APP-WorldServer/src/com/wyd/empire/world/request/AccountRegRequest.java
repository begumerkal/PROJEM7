package com.wyd.empire.world.request;

import com.wyd.empire.world.session.ConnectSession;

public class AccountRegRequest extends SessionRequest {
	public AccountRegRequest(int id, int sessionId, ConnectSession session) {
		super(IRequestType.ACCOUNT_REG, id, sessionId, session);
	}
}