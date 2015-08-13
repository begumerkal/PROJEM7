package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetIntroduceOk extends AbstractData {
	private String cultureDesc; // 宠物培养说明
	private String trainDesc;   // 宠物训练说明
	private String inheritance; // 宠物传承说明
	public GetIntroduceOk(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_GetIntroduceOk, sessionId, serial);
    }
	public GetIntroduceOk(){
		 super(Protocol.MAIN_PET, Protocol.PET_GetIntroduceOk);
	}
	public String getCultureDesc() {
		return cultureDesc;
	}
	public void setCultureDesc(String cultureDesc) {
		this.cultureDesc = cultureDesc;
	}
	public String getTrainDesc() {
		return trainDesc;
	}
	public void setTrainDesc(String trainDesc) {
		this.trainDesc = trainDesc;
	}
	public String getInheritance() {
		return inheritance;
	}
	public void setInheritance(String inheritance) {
		this.inheritance = inheritance;
	}

}
