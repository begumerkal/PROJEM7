package com.wyd.empire.protocol.data.recycle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class RecycleItemOk extends AbstractData {	
	

    public RecycleItemOk(int sessionId, int serial) {
        super(Protocol.MAIN_RECYCLE, Protocol.RECYCLE_RecycleItemOk, sessionId, serial);
    }

    public RecycleItemOk() {
        super(Protocol.MAIN_RECYCLE, Protocol.RECYCLE_RecycleItemOk);
    }

	
    
}
