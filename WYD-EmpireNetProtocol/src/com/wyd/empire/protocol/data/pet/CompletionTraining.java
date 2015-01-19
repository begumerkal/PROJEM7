package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class CompletionTraining extends AbstractData {
	
	public CompletionTraining(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_CompletionTraining, sessionId, serial);
    }
	public CompletionTraining() {
        super(Protocol.MAIN_PET, Protocol.PET_CompletionTraining);
    }
	

}
