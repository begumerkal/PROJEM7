package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class PunchOk extends AbstractData {
	
	private int stoneType;        //打孔类型（1：攻击，2防御，3特殊）
	private int mark;		 //操作类型（0是打孔，1是拆卸）
	private int itemId;//宝石id（打孔返回0）
	private int result;     //操作结果（0表示成功，1表示不扣石头的失败，2是扣石头的失败）
	
    public PunchOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_PunchOk, sessionId, serial);
    }

    public PunchOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_PunchOk);
    }

	public int getStoneType() {
		return stoneType;
	}

	public void setStoneType(int stoneType) {
		this.stoneType = stoneType;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
    
}
