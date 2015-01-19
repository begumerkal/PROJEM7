package com.wyd.empire.protocol.data.purchase;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SubmitSMSProduct extends AbstractData {
    private String serialNum;
	public SubmitSMSProduct(int sessionId, int serial) {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_SubmitSMSProduct, sessionId, serial);
	}

	public SubmitSMSProduct() {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_SubmitSMSProduct);
	}

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
}
