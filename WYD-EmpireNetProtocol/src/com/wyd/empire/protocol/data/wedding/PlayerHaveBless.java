package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class PlayerHaveBless extends AbstractData {

	private int blessingNum;
	
    public PlayerHaveBless(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_PlayerHaveBless, sessionId, serial);
    }

    public PlayerHaveBless() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_PlayerHaveBless);
    }

	public int getBlessingNum() {
		return blessingNum;
	}

	public void setBlessingNum(int blessingNum) {
		this.blessingNum = blessingNum;
	}

 
}
