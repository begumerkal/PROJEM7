package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetNearbyReceivedMailListOk extends AbstractData {
	private int playerId;
	private int[] mailId;
	private String[] theme;
	private String[] sendName;
	private String[] sendTime;
	private boolean[] isRead;
	private int pageNum;
	private int totalPage;
	public GetNearbyReceivedMailListOk(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyReceivedMailListOk, sessionId, serial);
	}

	public GetNearbyReceivedMailListOk() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyReceivedMailListOk);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int[] getMailId() {
		return mailId;
	}

	public void setMailId(int[] mailId) {
		this.mailId = mailId;
	}

	public String[] getTheme() {
		return theme;
	}

	public void setTheme(String[] theme) {
		this.theme = theme;
	}

	public String[] getSendName() {
		return sendName;
	}

	public void setSendName(String[] sendName) {
		this.sendName = sendName;
	}

	public String[] getSendTime() {
		return sendTime;
	}

	public void setSendTime(String[] sendTime) {
		this.sendTime = sendTime;
	}

	public boolean[] getIsRead() {
		return isRead;
	}

	public void setIsRead(boolean[] isRead) {
		this.isRead = isRead;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
