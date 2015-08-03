package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class InheritanceOk extends AbstractData {
	
	public InheritanceOk(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_InheritanceOk, sessionId, serial);
    }
	public InheritanceOk() {
        super(Protocol.MAIN_PET, Protocol.PET_InheritanceOk);
    }
	

}
