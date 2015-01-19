package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetPeopleInfo extends AbstractData {

	private String wedNum;   //婚礼编号
    public GetPeopleInfo(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetPeopleInfo, sessionId, serial);
    }

    public GetPeopleInfo() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetPeopleInfo);
    }

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}

}
