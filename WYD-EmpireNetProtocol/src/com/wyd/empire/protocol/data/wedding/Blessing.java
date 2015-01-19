package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class Blessing extends AbstractData {

	private String wedNum;		//婚礼编号
	
    public Blessing(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_Blessing, sessionId, serial);
    }

    public Blessing() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_Blessing);
    }

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}

}
