package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ExtWeddingOk extends AbstractData {
	
	private int playerId;

    public ExtWeddingOk(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_ExtWeddingOk, sessionId, serial);
    }

    public ExtWeddingOk() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_ExtWeddingOk);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

}
