package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class StartTrain extends AbstractData {
	private int petId;
	private int payment; // 支付方式0金币1钻石
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public StartTrain(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_StartTrain, sessionId, serial);
    }
	public StartTrain() {
        super(Protocol.MAIN_PET, Protocol.PET_StartTrain);
    }
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	

}
