package com.app.empire.protocol.data.recycle;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class RecycleItemOk extends AbstractData {	
	

    public RecycleItemOk(int sessionId, int serial) {
        super(Protocol.MAIN_RECYCLE, Protocol.RECYCLE_RecycleItemOk, sessionId, serial);
    }

    public RecycleItemOk() {
        super(Protocol.MAIN_RECYCLE, Protocol.RECYCLE_RecycleItemOk);
    }

	
    
}
