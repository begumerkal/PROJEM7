package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class DeleteNearbyMail extends AbstractData {
	private int myInfoId;
	private int[] mailId;
	public DeleteNearbyMail(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_DeleteNearbyMail, sessionId, serial);
	}

	public DeleteNearbyMail() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_DeleteNearbyMail);
	}

	public int getMyInfoId() {
		return myInfoId;
	}

	public void setMyInfoId(int myInfoId) {
		this.myInfoId = myInfoId;
	}

	public int[] getMailId() {
		return mailId;
	}

	public void setMailId(int[] mailId) {
		this.mailId = mailId;
	}
}
