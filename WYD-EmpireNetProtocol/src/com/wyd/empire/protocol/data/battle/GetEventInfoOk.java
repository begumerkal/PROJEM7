package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetEventInfoOk extends AbstractData {
    private int battleId;
	private int eventId;// 事件的id
	private String name;// 事件的名字
	private int effect1;// 事件的效果参数1
	private int effect2;// 事件的效果参数2
    public GetEventInfoOk(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GetEventInfoOk, sessionId, serial);
	}

	public GetEventInfoOk() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GetEventInfoOk);
	}
    public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getEffect1() {
		return effect1;
	}

	public void setEffect1(int effect1) {
		this.effect1 = effect1;
	}

	public int getEffect2() {
		return effect2;
	}

	public void setEffect2(int effect2) {
		this.effect2 = effect2;
	}

	
}
