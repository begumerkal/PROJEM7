package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetNearbyFriendListOk extends AbstractData {
	private int playerId;
	private int[] playerInfoId;
	private String[] avatarURL;
	private byte[] sex;
	private String[] playerName;
	private int[] fighting;
	private int[] distance;
	private int[] mailCount;
	private boolean[] isOnline;
    private String[] suitHead;
    private String[] suitFace;
    private int friendCount;
    private int maxCount;
	public GetNearbyFriendListOk(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyFriendListOk, sessionId, serial);
	}

	public GetNearbyFriendListOk() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyFriendListOk);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int[] getPlayerInfoId() {
		return playerInfoId;
	}

	public void setPlayerInfoId(int[] playerInfoId) {
		this.playerInfoId = playerInfoId;
	}

	public String[] getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String[] avatarURL) {
		this.avatarURL = avatarURL;
	}

    public String[] getSuitHead() {
        return suitHead;
    }

    public void setSuitHead(String[] suitHead) {
        this.suitHead = suitHead;
    }

    public String[] getSuitFace() {
        return suitFace;
    }

    public void setSuitFace(String[] suitFace) {
        this.suitFace = suitFace;
    }

    public byte[] getSex() {
		return sex;
	}

	public void setSex(byte[] sex) {
		this.sex = sex;
	}

	public String[] getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}

	public int[] getFighting() {
		return fighting;
	}

	public void setFighting(int[] fighting) {
		this.fighting = fighting;
	}

	public int[] getDistance() {
		return distance;
	}

	public void setDistance(int[] distance) {
		this.distance = distance;
	}

	public int[] getMailCount() {
		return mailCount;
	}

	public void setMailCount(int[] mailCount) {
		this.mailCount = mailCount;
	}

	public boolean[] getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(boolean[] isOnline) {
		this.isOnline = isOnline;
	}

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
