package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetInheritanceList extends AbstractData {
	public GetInheritanceList(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_GetInheritanceList, sessionId, serial);
    }
	public GetInheritanceList(){
		 super(Protocol.MAIN_PET, Protocol.PET_GetInheritanceList);
	}
}
