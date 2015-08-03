package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetIntroduce extends AbstractData {
	public GetIntroduce(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_GetIntroduce, sessionId, serial);
    }
	public GetIntroduce(){
		 super(Protocol.MAIN_PET, Protocol.PET_GetIntroduce);
	}

}
