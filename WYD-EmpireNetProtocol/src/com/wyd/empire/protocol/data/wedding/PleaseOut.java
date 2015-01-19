package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class PleaseOut extends AbstractData {
	
	private int playerId;
	private String  wedNum;			//婚礼编号

    public PleaseOut(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_PleaseOut, sessionId, serial);
    }

    public PleaseOut() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_PleaseOut);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}
 
}
