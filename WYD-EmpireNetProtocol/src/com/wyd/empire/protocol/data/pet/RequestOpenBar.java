package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class RequestOpenBar extends AbstractData {
	
	public RequestOpenBar(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_RequestOpenBar, sessionId, serial);
    }
    public RequestOpenBar() {
        super(Protocol.MAIN_PET, Protocol.PET_RequestOpenBar);
    }
	

}
