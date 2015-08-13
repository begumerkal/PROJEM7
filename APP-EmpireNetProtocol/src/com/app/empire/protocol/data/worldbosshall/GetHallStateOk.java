package com.app.empire.protocol.data.worldbosshall;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取挑战大厅状态
 * @author zengxc
 *
 */
public class GetHallStateOk extends AbstractData {
	private String[] playerName;
	private int[] hurt;
	public GetHallStateOk(int sessionId, int serial) {
        super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetHallStateOk, sessionId, serial);
    }
	public GetHallStateOk(){
		super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetHallStateOk);
	}
	public String[] getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}
	public int[] getHurt() {
		return hurt;
	}
	public void setHurt(int[] hurt) {
		this.hurt = hurt;
	}
	

}
