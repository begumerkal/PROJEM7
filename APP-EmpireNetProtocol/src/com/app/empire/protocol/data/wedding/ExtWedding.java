package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ExtWedding extends AbstractData {

	private String wedNum;	//婚礼编号
    public ExtWedding(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_ExtWedding, sessionId, serial);
    }

    public ExtWedding() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_ExtWedding);
    }

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}

}
