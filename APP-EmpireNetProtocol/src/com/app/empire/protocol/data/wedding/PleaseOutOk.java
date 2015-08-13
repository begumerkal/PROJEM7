package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
