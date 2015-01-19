package com.wyd.empire.protocol.data.rebirth;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Rebirth extends AbstractData {
    private int rebirthType;//废弃
	public Rebirth(int sessionId, int serial) {
		super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_Rebirth, sessionId, serial);
	}

	public Rebirth() {
		super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_Rebirth);
	}

	/**
	 * 转生类型 0普通转生，1完美转生
	 * @return
	 */
    public int getRebirthType() {
        return rebirthType;
    }

    public void setRebirthType(int rebirthType) {
        this.rebirthType = rebirthType;
    }
}
