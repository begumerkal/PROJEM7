package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class InheritanceOk extends AbstractData {
	
	public InheritanceOk(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_InheritanceOk, sessionId, serial);
    }
	public InheritanceOk() {
        super(Protocol.MAIN_PET, Protocol.PET_InheritanceOk);
    }
	

}
