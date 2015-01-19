package com.wyd.empire.world.room;

import com.wyd.empire.world.player.WorldPlayer;

/**
 * 类 <code>Seat</code> 房间座位信息
 * 
 * @author Administrator
 * 
 */
public class Seat {
	/** 座位上的用户 */
	private WorldPlayer player;

	/** 用户所属阵营 */
	private int camp;

	/** 用户是否准备 */
	private boolean ready = false;

	/** 座位号 */
	private int seatIndex;

	/** 是否使用 */
	private boolean used;

	/** 是否机器人 */
	private boolean robot = false;

	public WorldPlayer getPlayer() {
		return player;
	}

	public void setPlayer(WorldPlayer player) {
		this.player = player;
	}

	public int getCamp() {
		return camp;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public int getSeatIndex() {
		return seatIndex;
	}

	public void setSeatIndex(int seatIndex) {
		this.seatIndex = seatIndex;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isRobot() {
		return robot;
	}

	public void setRobot(boolean robot) {
		this.robot = robot;
	}
}
