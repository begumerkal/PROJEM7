package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetConsumeInfo extends AbstractData {
	
	private int	infoType;	//信息类型（1合成，2镶嵌，3打孔）

	
    public GetConsumeInfo(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetConsumeInfo, sessionId, serial);
    }

    public GetConsumeInfo() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetConsumeInfo);
    }

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }
}
