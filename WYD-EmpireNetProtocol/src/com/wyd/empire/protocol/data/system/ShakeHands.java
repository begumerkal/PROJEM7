package com.wyd.empire.protocol.data.system;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class ShakeHands extends AbstractData {

	private int code;
	
    public ShakeHands(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_ShakeHands, sessionId, serial);
    }

    public ShakeHands() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_ShakeHands);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
