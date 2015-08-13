package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
