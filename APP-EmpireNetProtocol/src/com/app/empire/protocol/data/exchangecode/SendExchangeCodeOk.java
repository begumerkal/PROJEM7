package com.app.empire.protocol.data.exchangecode;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

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
