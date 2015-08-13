package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
