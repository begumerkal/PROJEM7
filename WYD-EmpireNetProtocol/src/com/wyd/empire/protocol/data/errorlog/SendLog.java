package com.wyd.empire.protocol.data.errorlog;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SendLog extends AbstractData {
    private String logfilename;
    private String[] logs;
	public SendLog(int sessionId, int serial) {
		super(Protocol.MAIN_ERRORLOG, Protocol.ERRORLOG_SendLogList, sessionId, serial);
	}

	public SendLog() {
		super(Protocol.MAIN_ERRORLOG, Protocol.ERRORLOG_SendLogList);
	}

    public String getLogfilename() {
        return logfilename;
    }

    public void setLogfilename(String logfilename) {
        this.logfilename = logfilename;
    }

    public String[] getLogs() {
        return logs;
    }

    public void setLogs(String[] logs) {
        this.logs = logs;
    }
}
