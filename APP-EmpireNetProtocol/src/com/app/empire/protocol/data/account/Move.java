package com.app.empire.protocol.data.account;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/***
 * 玩家移动
 * 
 * @author doter
 *
 */
public class Move extends AbstractData {
	private int playerId;
	private byte direction;// 方向1-12
	private int width;// 所在宽度位置
	private int high;// 所在高度位置

	public Move(int sessionId, int serial) {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Move, sessionId, serial);
	}
	public Move() {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Move);
	}

	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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
