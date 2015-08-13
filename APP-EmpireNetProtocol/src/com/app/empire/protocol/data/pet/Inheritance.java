package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class Inheritance extends AbstractData {
	private int inheritanceId;//		传承id
	private int beInheriedId;//	被传承id
	public Inheritance(int sessionId, int serial) {
		super(Protocol.MAIN_PET, Protocol.PET_Inheritance, sessionId,
				serial);
	}

	public Inheritance() {
		super(Protocol.MAIN_PET, Protocol.PET_Inheritance);
	}

	public int getInheritanceId() {
		return inheritanceId;
	}

	public void setInheritanceId(int inheritanceId) {
		this.inheritanceId = inheritanceId;
	}

	public int getBeInheriedId() {
		return beInheriedId;
	}

	public void setBeInheriedId(int beInheriedId) {
		this.beInheriedId = beInheriedId;
	}

}
