package com.wyd.empire.protocol.data.errorlog;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetLog extends AbstractData {
    private String logfilename;
	public GetLog(int sessionId, int serial) {
		super(Protocol.MAIN_ERRORLOG, Protocol.ERRORLOG_GetLog, sessionId, serial);
	}

	public GetLog() {
		super(Protocol.MAIN_ERRORLOG, Protocol.ERRORLOG_GetLog);
	}

    public String getLogfilename() {
        return logfilename;
    }

    public void setLogfilename(String logfilename) {
        this.logfilename = logfilename;
    }
}
