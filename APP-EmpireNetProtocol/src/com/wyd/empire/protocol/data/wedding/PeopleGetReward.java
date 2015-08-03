package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class PeopleGetReward extends AbstractData {

	private String wedNum;		//婚礼编号
	
    public PeopleGetReward(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_PeopleGetReward, sessionId, serial);
    }

    public PeopleGetReward() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_PeopleGetReward);
    }

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}

}
