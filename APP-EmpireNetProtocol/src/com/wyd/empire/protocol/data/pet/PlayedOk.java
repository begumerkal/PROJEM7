package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
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
