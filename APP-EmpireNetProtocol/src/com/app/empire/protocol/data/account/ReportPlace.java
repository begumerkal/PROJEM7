package com.app.empire.protocol.data.account;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/***
 * 告知服务器玩家位置
 * 
 * @author doter
 */

public class ReportPlace extends AbstractData {
	private byte direction;// 方向1-12
	private int width;// 所在宽度位置
	private int high;// 所在高度位置

	public ReportPlace(int sessionId, int serial) {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ReportPlace, sessionId, serial);
	}
	public ReportPlace() {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ReportPlace);
	}

	public byte getDirection() {
		return direction;
	}
	public void setDirection(byte direction) {
		this.direction = direction;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}

}
