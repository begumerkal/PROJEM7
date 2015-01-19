package com.wyd.empire.protocol.data.purchase;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class IOSSendProductCheckInfo extends AbstractData {
	private String orderNum;
	private int playerId;
	private String key;
	private int channelId;

	public IOSSendProductCheckInfo(int sessionId, int serial) {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_IOSSendProductCheckInfo, sessionId, serial);
	}

	public IOSSendProductCheckInfo() {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_IOSSendProductCheckInfo);
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }
}
