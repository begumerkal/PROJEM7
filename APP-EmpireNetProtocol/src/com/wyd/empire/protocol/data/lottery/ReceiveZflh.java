package com.wyd.empire.protocol.data.lottery;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ReceiveZflh extends AbstractData {
	private int id;//祝福礼盒ID
	public ReceiveZflh(int sessionId, int serial) {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveZflh, sessionId, serial);
	}

	public ReceiveZflh() {
		super(Protocol.MAIN_LOTTERY, Protocol.LOTTERY_ReceiveZflh);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
