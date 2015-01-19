package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ReceivePetCultureValue extends AbstractData {
	private int cultureAddHp;
	private int cultureAddAttack;
	private int cultureAddDefend;
	public int getCultureAddHp() {
		return cultureAddHp;
	}
	public void setCultureAddHp(int cultureAddHp) {
		this.cultureAddHp = cultureAddHp;
	}
	public int getCultureAddAttack() {
		return cultureAddAttack;
	}
	public void setCultureAddAttack(int cultureAddAttack) {
		this.cultureAddAttack = cultureAddAttack;
	}
	public int getCultureAddDefend() {
		return cultureAddDefend;
	}
	public void setCultureAddDefend(int cultureAddDefend) {
		this.cultureAddDefend = cultureAddDefend;
	}
	public ReceivePetCultureValue(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_ReceivePetCultureValue, sessionId, serial);
    }
	public ReceivePetCultureValue() {
        super(Protocol.MAIN_PET, Protocol.PET_ReceivePetCultureValue);
    }

}
