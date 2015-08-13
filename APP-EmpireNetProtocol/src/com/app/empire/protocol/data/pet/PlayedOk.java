package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @author zengxc
 *
 */
public class PlayedOk extends AbstractData {
	public PlayedOk(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_PlayedOk, sessionId, serial);
    }
	public PlayedOk(){
		 super(Protocol.MAIN_PET, Protocol.PET_PlayedOk);
	}

}
