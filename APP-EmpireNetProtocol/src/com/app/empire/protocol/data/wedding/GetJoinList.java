package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetJoinList extends AbstractData {

	private String wedNum;		//婚礼编号
	
    public GetJoinList(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetJoinList, sessionId, serial);
    }

    public GetJoinList() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetJoinList);
    }

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}

}
