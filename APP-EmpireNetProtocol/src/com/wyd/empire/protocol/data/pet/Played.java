package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 
 * @author zengxc
 *
 */
public class Played extends AbstractData {
	private int petId;
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public Played(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_Played, sessionId, serial);
    }
	public Played(){
		 super(Protocol.MAIN_PET, Protocol.PET_Played);
	}

}
