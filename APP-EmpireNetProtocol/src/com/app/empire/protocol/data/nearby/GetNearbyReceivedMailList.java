package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetNearbyReceivedMailList extends AbstractData {
	private int myInfoId;
	private int pageNum;
	public GetNearbyReceivedMailList(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyReceivedMailList, sessionId, serial);
	}

	public GetNearbyReceivedMailList() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyReceivedMailList);
	}

	public int getMyInfoId() {
		return myInfoId;
	}

	public void setMyInfoId(int myInfoId) {
		this.myInfoId = myInfoId;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}
