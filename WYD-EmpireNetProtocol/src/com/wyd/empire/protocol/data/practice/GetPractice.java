package com.wyd.empire.protocol.data.practice;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 获取修炼列表
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class GetPractice extends AbstractData {
	private int playerId;//玩家id
	
    public GetPractice(int sessionId, int serial) {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_GetPractice, sessionId, serial);
    }

    public GetPractice() {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_GetPractice);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
    
    
}
