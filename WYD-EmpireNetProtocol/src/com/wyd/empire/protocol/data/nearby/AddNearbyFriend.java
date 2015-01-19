package com.wyd.empire.protocol.data.nearby;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class AddNearbyFriend extends AbstractData {
	private int myInfoId;
	private int friendInfoId;
	private byte refreshMark;
	public AddNearbyFriend(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_AddNearbyFriend,
				sessionId, serial);
	}

	public AddNearbyFriend() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_AddNearbyFriend);
	}

	public int getMyInfoId() {
		return myInfoId;
	}

	public void setMyInfoId(int myInfoId) {
		this.myInfoId = myInfoId;
	}

	public int getFriendInfoId() {
		return friendInfoId;
	}

	public void setFriendInfoId(int friendInfoId) {
		this.friendInfoId = friendInfoId;
	}

    public byte getRefreshMark() {
        return refreshMark;
    }

    public void setRefreshMark(byte refreshMark) {
        this.refreshMark = refreshMark;
    }
}
