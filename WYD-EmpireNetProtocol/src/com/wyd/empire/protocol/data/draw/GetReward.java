package com.wyd.empire.protocol.data.draw;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetReward extends AbstractData {
	
	private int starNum;		//领取奖励的星数
	private int typeId;
	
    public GetReward(int sessionId, int serial) {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_GetReward, sessionId, serial);
    }

    public GetReward() {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_GetReward);
    }

	public int getStarNum() {
		return starNum;
	}

	public void setStarNum(int starNum) {
		this.starNum = starNum;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
}
