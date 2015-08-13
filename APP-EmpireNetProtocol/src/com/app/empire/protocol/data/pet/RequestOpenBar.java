package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class RequestOpenBar extends AbstractData {
	
	public RequestOpenBar(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_RequestOpenBar, sessionId, serial);
    }
    public RequestOpenBar() {
        super(Protocol.MAIN_PET, Protocol.PET_RequestOpenBar);
    }
	

}
