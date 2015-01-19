package com.wyd.empire.protocol.data.errorcode;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class CheckOk extends AbstractData {
    private int checkVersion;
	public CheckOk(int sessionId, int serial) {
		super(Protocol.MAIN_ERRORCODE, Protocol.ERRORCODE_CheckOk, sessionId, serial);
	}

	public CheckOk() {
		super(Protocol.MAIN_ERRORCODE, Protocol.ERRORCODE_CheckOk);
	}

    public int getCheckVersion() {
        return checkVersion;
    }

    public void setCheckVersion(int checkVersion) {
        this.checkVersion = checkVersion;
    }
}
