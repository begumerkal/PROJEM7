package com.wyd.empire.world.room;

public class InviteInfo {
	private long inviteTime;
	private int inviteCount;

	public long getInviteTime() {
		return inviteTime;
	}

	public void setInviteTime(long inviteTime) {
		this.inviteTime = inviteTime;
	}

	public int getInviteCount() {
		return inviteCount;
	}

	public void setInviteCount(int inviteCount) {
		this.inviteCount = inviteCount;
	}

	public void addInviteCount() {
		this.inviteCount++;
	}
}
