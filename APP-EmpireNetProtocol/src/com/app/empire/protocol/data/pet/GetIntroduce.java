package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetIntroduce extends AbstractData {
	public GetIntroduce(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_GetIntroduce, sessionId, serial);
    }
	public GetIntroduce(){
		 super(Protocol.MAIN_PET, Protocol.PET_GetIntroduce);
	}

}
