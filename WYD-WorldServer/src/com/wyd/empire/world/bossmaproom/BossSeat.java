package com.wyd.empire.world.bossmaproom;

import com.wyd.empire.world.player.WorldPlayer;

/**
 * 房间座位信息
 * 
 * @author Administrator
 *
 */
public class BossSeat {
	private WorldPlayer player; // 座位上的用户
	private int camp; // 用户所属阵营
	private boolean ready = false;// 用户是否准备
	private int seatIndex; // 座位号
	private boolean used;

	public WorldPlayer getPlayer() {
		return player;
	}

	public void setPlayer(WorldPlayer player) {
		this.player = player;
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

	public int getCamp() {
		return camp;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}

}
