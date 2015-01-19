package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class DetermineCulture extends AbstractData {
	private int petId;//
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public DetermineCulture(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_CompletionTraining, sessionId, serial);
    }
	public DetermineCulture() {
        super(Protocol.MAIN_PET, Protocol.PET_CompletionTraining);
    }

}
