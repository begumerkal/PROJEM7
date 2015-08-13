package com.app.empire.protocol.data.player;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetStrengthenInfoOk extends AbstractData {
	private String detail;

    public GetStrengthenInfoOk(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetStrengthenInfoOk, sessionId, serial);
    }

    public GetStrengthenInfoOk() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetStrengthenInfoOk);
    }

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
