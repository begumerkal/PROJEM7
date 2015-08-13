package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SendBossList extends AbstractData {
	private int[] bossmapId;	//	副本id
	private String[] bossmapShortName;	//	副本名称缩写
	private int[] bossmapState;	//	副本状态（未开启-0，已开启-1，已打过-3）
	private int[] bossmapLevel;	//	副本星级（未开启副本星级为0）

	public SendBossList(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SendBossList, sessionId, serial);
	}

	public SendBossList() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SendBossList);
	}

	public int[] getBossmapId() {
		return bossmapId;
	}

	public void setBossmapId(int[] bossmapId) {
		this.bossmapId = bossmapId;
	}

	public String[] getBossmapShortName() {
		return bossmapShortName;
	}

	public void setBossmapShortName(String[] bossmapShortName) {
		this.bossmapShortName = bossmapShortName;
	}

	public int[] getBossmapState() {
		return bossmapState;
	}

	public void setBossmapState(int[] bossmapState) {
		this.bossmapState = bossmapState;
	}

	public int[] getBossmapLevel() {
		return bossmapLevel;
	}

	public void setBossmapLevel(int[] bossmapLevel) {
		this.bossmapLevel = bossmapLevel;
	}

	
	
	
}
