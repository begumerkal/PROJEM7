package com.app.empire.protocol.data.card;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetPlayerCardsOk extends AbstractData {
	private int[] cardId;//
	private String[] data;//
	private String[] groupAddition;//

	public GetPlayerCardsOk(int sessionId, int serial) {
        super(Protocol.MAIN_CARD, Protocol.CARD_GetPlayerCardsOk, sessionId, serial);
    }
	public GetPlayerCardsOk(){
		 super(Protocol.MAIN_CARD, Protocol.CARD_GetPlayerCardsOk);
	}
	public int[] getCardId() {
		return cardId;
	}
	public void setCardId(int[] cardId) {
		this.cardId = cardId;
	}
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	public String[] getGroupAddition() {
		return groupAddition;
	}
	public void setGroupAddition(String[] groupAddition) {
		this.groupAddition = groupAddition;
	}
	
}
