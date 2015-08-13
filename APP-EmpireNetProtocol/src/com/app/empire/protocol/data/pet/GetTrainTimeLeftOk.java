package com.app.empire.protocol.data.pet;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetTrainTimeLeftOk extends AbstractData {
	private int trainTimeLeft;//训练完成还需要的时间(秒)
	private int diamondMin;   //立即完成需要的钻石的单价(钻/分)
	public GetTrainTimeLeftOk(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_GetTrainTimeLeftOk, sessionId, serial);
    }
	public GetTrainTimeLeftOk() {
        super(Protocol.MAIN_PET, Protocol.PET_GetTrainTimeLeftOk);
    }
	public int getTrainTimeLeft() {
		return trainTimeLeft;
	}
	public void setTrainTimeLeft(int trainTimeLeft) {
		this.trainTimeLeft = trainTimeLeft;
	}
	public int getDiamondMin() {
		return diamondMin;
	}
	public void setDiamondMin(int diamondMin) {
		this.diamondMin = diamondMin;
	}
	

}
