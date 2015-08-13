package com.app.empire.protocol.data.nearby;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetNearbyPlayerListOk extends AbstractData {
	private int playerId;
	private int[] playerInfoId;
	private String[] avatarURL;
	private byte[] sex;
	private String[] playerName;
	private int[] fighting;
	private int[] distance;
	private int[] mailCount;
	private boolean[] isOnline;
	private boolean[] isFriend;
    private String[] suitHead;
    private String[] suitFace;
	public GetNearbyPlayerListOk(int sessionId, int serial) {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyPlayerListOk, sessionId, serial);
	}

	public GetNearbyPlayerListOk() {
		super(Protocol.MAIN_NEARBY, Protocol.NEARBY_GetNearbyPlayerListOk);
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

    public boolean[] getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean[] isFriend) {
        this.isFriend = isFriend;
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
}
