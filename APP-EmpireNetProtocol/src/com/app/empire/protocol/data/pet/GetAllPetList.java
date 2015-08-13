package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetAllPetList extends AbstractData {
	public GetAllPetList(int sessionId, int serial) {
		super(Protocol.MAIN_PET, Protocol.PET_GetAllPetList, sessionId, serial);
	}

	public GetAllPetList() {
		super(Protocol.MAIN_PET, Protocol.PET_GetAllPetList);
	}
	

}
