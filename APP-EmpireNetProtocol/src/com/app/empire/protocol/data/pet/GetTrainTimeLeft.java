package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetTrainTimeLeft extends AbstractData {
	public GetTrainTimeLeft(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_GetTrainTimeLeft, sessionId, serial);
    }
	
	public GetTrainTimeLeft() {
        super(Protocol.MAIN_PET, Protocol.PET_GetTrainTimeLeft);
    }
	

}
