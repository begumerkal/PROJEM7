package com.wyd.empire.world.room;

public class RandomRoom {
	private Room room;
	private boolean ready = false;
	private int count = 0;
	private int conId;// 公会ID

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
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

	public int getConId() {
		return conId;
	}

	public void setConId(int conId) {
		this.conId = conId;
	}

	public int getAverageFighting() {
		return room.getAvgFighting();
	}
}
