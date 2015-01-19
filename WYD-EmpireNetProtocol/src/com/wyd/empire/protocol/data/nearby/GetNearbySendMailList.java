package com.wyd.empire.protocol.data.nearby;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetNearbySendMailList extends AbstractData {
	private int myInfoId;
	private int pageNum;
	public GetNearbySendMailList(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbySendMailList, sessionId, serial);
	}

	public GetNearbySendMailList() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbySendMailList);
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
