package com.wyd.empire.protocol.data.purchase;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetCallBackUriOk extends AbstractData {
    private String ip;
    private String port;
	public GetCallBackUriOk(int sessionId, int serial) {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_GetCallBackUriOk, sessionId, serial);
	}

	public GetCallBackUriOk() {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_GetCallBackUriOk);
	}
	
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
