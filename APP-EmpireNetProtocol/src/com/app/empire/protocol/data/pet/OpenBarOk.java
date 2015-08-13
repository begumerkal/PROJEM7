package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 开槽成功
 * @author zengxc
 *
 */
public class OpenBarOk extends AbstractData {
	
	public OpenBarOk(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_OpenBarOk, sessionId, serial);
    }
	public OpenBarOk() {
        super(Protocol.MAIN_PET, Protocol.PET_OpenBarOk);
    }
	
	
}
