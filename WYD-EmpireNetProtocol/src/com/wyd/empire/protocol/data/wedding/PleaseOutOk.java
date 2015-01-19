package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class PleaseOutOk extends AbstractData {

	private int playerid;
	
    public PleaseOutOk(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_PleaseOutOk, sessionId, serial);
    }

    public PleaseOutOk() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_PleaseOutOk);
    }

	public int getPlayerid() {
		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}
 
}
