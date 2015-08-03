package com.wyd.empire.protocol.data.nearby;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class UpdatePlayerInfo extends AbstractData {
    private int myInfoId;
    private int longitude;
    private int latitude;
    private int serviceId;
    private int playerId;
    private String avatarURL;
    private byte sex;
    private String playerName;
    private int fighting;
    private String suitHead;
    private String suitFace;
    
    public UpdatePlayerInfo(int sessionId, int serial) {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_UpdatePlayerInfo, sessionId, serial);
    }

    public UpdatePlayerInfo() {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_UpdatePlayerInfo);
    }

    public int getMyInfoId() {
        return myInfoId;
    }

    public void setMyInfoId(int myInfoId) {
        this.myInfoId = myInfoId;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getFighting() {
        return fighting;
    }

    public void setFighting(int fighting) {
        this.fighting = fighting;
    }

    public String getSuitHead() {
        return suitHead;
    }

    public void setSuitHead(String suitHead) {
        this.suitHead = suitHead;
    }

    public String getSuitFace() {
        return suitFace;
    }

    public void setSuitFace(String suitFace) {
        this.suitFace = suitFace;
    }
}
