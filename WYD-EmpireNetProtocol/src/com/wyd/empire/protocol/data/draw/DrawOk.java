package com.wyd.empire.protocol.data.draw;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class DrawOk extends AbstractData {
	
	private int	price;		//抽奖单价
	private int	starNum;	//玩家已经获得的星星数
    private String otherReward;		//额外奖励
	
    public DrawOk(int sessionId, int serial) {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_DrawOk, sessionId, serial);
    }

    public DrawOk() {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_DrawOk);
    }

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStarNum() {
		return starNum;
	}

	public void setStarNum(int starNum) {
		this.starNum = starNum;
	}

	public String getOtherReward() {
		return otherReward;
	}

	public void setOtherReward(String otherReward) {
		this.otherReward = otherReward;
	}
}
