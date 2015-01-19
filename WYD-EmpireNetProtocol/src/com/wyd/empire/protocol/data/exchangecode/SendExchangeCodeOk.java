package com.wyd.empire.protocol.data.exchangecode;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SendExchangeCodeOk extends AbstractData {
    private String code;
	public SendExchangeCodeOk(int sessionId, int serial) {
		super(Protocol.MAIN_EXCHANGECODE, Protocol.EXCHANGECODE_SendExchangeCodeOk, sessionId, serial);
	}

	public SendExchangeCodeOk() {
		super(Protocol.MAIN_EXCHANGECODE, Protocol.EXCHANGECODE_SendExchangeCodeOk);
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
