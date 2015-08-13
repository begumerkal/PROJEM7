package com.app.empire.protocol.data.starsoul;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取星图列表
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class GetStarSoul extends AbstractData {
	private int playerId;//玩家id
	
    public GetStarSoul(int sessionId, int serial) {
        super(Protocol.MAIN_STARSOUL, Protocol.STARSOUL_GetStarSoul, sessionId, serial);
    }

    public GetStarSoul() {
        super(Protocol.MAIN_STARSOUL, Protocol.STARSOUL_GetStarSoul);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
    
    
}
