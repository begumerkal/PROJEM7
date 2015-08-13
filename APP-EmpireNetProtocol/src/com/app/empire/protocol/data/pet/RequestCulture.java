package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class RequestCulture extends AbstractData {
	private int petId;
	private int payment; // 支付方式0金币1钻石
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public RequestCulture(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_RequestCulture, sessionId, serial);
    }
	public RequestCulture() {
        super(Protocol.MAIN_PET, Protocol.PET_RequestCulture);
    }
	

}
