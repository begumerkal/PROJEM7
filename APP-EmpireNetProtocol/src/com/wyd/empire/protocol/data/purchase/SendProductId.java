package com.wyd.empire.protocol.data.purchase;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SendProductId extends AbstractData {
	private String productId;
	
	public SendProductId(int sessionId, int serial) {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_SendProductId, sessionId, serial);
	}

	public SendProductId() {
		super(Protocol.MAIN_PURCHASE, Protocol.PURCHASE_SendProductId);
	}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
