package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SummonOk extends AbstractData {
	public SummonOk(int sessionId, int serial) {
		super(Protocol.MAIN_PET, Protocol.PET_SummonOk, sessionId, serial);
	}

	public SummonOk() {
		super(Protocol.MAIN_PET, Protocol.PET_SummonOk);
	}
	
	private String name;		//宠物名称

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
