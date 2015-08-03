package com.wyd.empire.protocol.data.star;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class UpgradeOk extends AbstractData {
	

    public UpgradeOk(int sessionId, int serial) {
        super(Protocol.MAIN_STAR, Protocol.STAR_UpgradeOk, sessionId, serial);
    }

    public UpgradeOk() {
        super(Protocol.MAIN_STAR, Protocol.STAR_UpgradeOk);
    }

	
	

}
