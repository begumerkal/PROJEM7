package com.wyd.empire.protocol.data.singlemap;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class ChallengeSuccess extends AbstractData {
	private int pointId; //弃用。不安全，改用worldplayer里的
	private byte[] battleVerify; //战斗检验（加密）

	public ChallengeSuccess(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_ChallengeSuccess, sessionId, serial);
    }
	public ChallengeSuccess(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_ChallengeSuccess);
	}
	public int getPointId() {
		return pointId;
	}
	public void setPointId(int pointId) {
		this.pointId = pointId;
	}
	public byte[] getBattleVerify() {
		return battleVerify;
	}
	public void setBattleVerify(byte[] battleVerify) {
		this.battleVerify = battleVerify;
	}
	

}
