package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class Summon extends AbstractData {
	public Summon(int sessionId, int serial) {
		super(Protocol.MAIN_PET, Protocol.PET_Summon, sessionId, serial);
	}

	public Summon() {
		super(Protocol.MAIN_PET, Protocol.PET_Summon);
	}
	
	private int petId;		//宠物ID
	private int paytype;	//支付类型

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

}
