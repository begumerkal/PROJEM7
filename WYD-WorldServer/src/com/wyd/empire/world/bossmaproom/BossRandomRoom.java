package com.wyd.empire.world.bossmaproom;

public class BossRandomRoom {
	private BossRoom room;
	private boolean ready = false;
	private int count = 0;

	public BossRoom getRoom() {
		return room;
	}

	public void setRoom(BossRoom room) {
		this.room = room;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
}
