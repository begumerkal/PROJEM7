package com.app.empire.protocol.data.exchangecode;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SendExchangeCode extends AbstractData {
    private String message;
	public SendExchangeCode(int sessionId, int serial) {
		super(Protocol.MAIN_EXCHANGECODE, Protocol.EXCHANGECODE_SendExchangeCode, sessionId, serial);
	}

	public SendExchangeCode() {
		super(Protocol.MAIN_EXCHANGECODE, Protocol.EXCHANGECODE_SendExchangeCode);
	}
	
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
