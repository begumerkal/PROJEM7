package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GiveReward extends AbstractData {

	private int rewardNum;   //红包数量
	private String wedNum;	 //婚礼编号
	
    public GiveReward(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GiveReward, sessionId, serial);
    }

    public GiveReward() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GiveReward);
    }

	public int getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}

}
