package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class Punch extends AbstractData {
	
	private int itemId;
	private int stoneType;        //打孔类型（1：攻击，2防御，3特殊）
	private int mark;		 //操作类型（0是打孔，1是拆卸）
	private int doType;			//选择类型（用于拆卸的，免费拆卸是0，钻石拆卸是1；打孔默认0）
	
    public Punch(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_Punch, sessionId, serial);
    }

    public Punch() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_Punch);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
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

	public int getDoType() {
		return doType;
	}

	public void setDoType(int doType) {
		this.doType = doType;
	}
}
