package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetInheritanceList extends AbstractData {
	public GetInheritanceList(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_GetInheritanceList, sessionId, serial);
    }
	public GetInheritanceList(){
		 super(Protocol.MAIN_PET, Protocol.PET_GetInheritanceList);
	}
}
