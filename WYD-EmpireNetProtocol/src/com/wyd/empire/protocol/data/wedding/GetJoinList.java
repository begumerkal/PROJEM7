package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
