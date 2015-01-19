package com.wyd.empire.protocol.data.bossmaproom;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class EnterRoomOk extends AbstractData {
    private int       roomId;
    private int       playerNumMode;
    private int       mapId;
    private int       wnersId;
    private int       playerNum;
    private boolean[] seatUsed;
    private int[]     playerId;
    private String[]  playerName;
    private int[]     playerLevel;
    private boolean[] playerReady;
    private int[]     playerSex;
    private String[]  playerEquipment;
    private int[]     playerProficiency;
    private String    mapName;
    private String    mapShortName;
    private int[]     playerEquipmentLevel;
    private int[]     vipLevel;
    private String[]  playerWing;
    private String[]  player_title;
    private int[]     qualifyingLevel;     // 排位等级
    private int[]     skillType;
    private int[]     skillLevel;
    private int[]     zsleve;
    private boolean[] doubleMark;
    private int       difficulty;          // 副本难度(1=普通,2=困难,3=地狱)
    private int[]     playerStar;          // 房间内玩家的副本星级
    private boolean[] isvip;
    private int       points; //当前副本的进度

    public EnterRoomOk(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_EnterRoomOk, sessionId, serial);
    }

    public EnterRoomOk() {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_EnterRoomOk);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getPlayerNumMode() {
        return playerNumMode;
    }

    public void setPlayerNumMode(int playerNumMode) {
        this.playerNumMode = playerNumMode;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getWnersId() {
        return wnersId;
    }

    public void setWnersId(int wnersId) {
        this.wnersId = wnersId;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public boolean[] getSeatUsed() {
        return seatUsed;
    }

    public void setSeatUsed(boolean[] seatUsed) {
        this.seatUsed = seatUsed;
    }

    public int[] getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int[] playerId) {
        this.playerId = playerId;
    }

    public String[] getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String[] playerName) {
        this.playerName = playerName;
    }

    public int[] getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int[] playerLevel) {
        this.playerLevel = playerLevel;
    }

    public boolean[] getPlayerReady() {
        return playerReady;
    }

    public void setPlayerReady(boolean[] playerReady) {
        this.playerReady = playerReady;
    }

    public int[] getPlayerSex() {
        return playerSex;
    }

    public void setPlayerSex(int[] playerSex) {
        this.playerSex = playerSex;
    }

    public String[] getPlayerEquipment() {
        return playerEquipment;
    }

    public void setPlayerEquipment(String[] playerEquipment) {
        this.playerEquipment = playerEquipment;
    }

    public int[] getPlayerProficiency() {
        return playerProficiency;
    }

    public void setPlayerProficiency(int[] playerProficiency) {
        this.playerProficiency = playerProficiency;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapShortName() {
        return mapShortName;
    }

    public void setMapShortName(String mapShortName) {
        this.mapShortName = mapShortName;
    }

    public int[] getPlayerEquipmentLevel() {
        return playerEquipmentLevel;
    }

    public void setPlayerEquipmentLevel(int[] playerEquipmentLevel) {
        this.playerEquipmentLevel = playerEquipmentLevel;
    }

    public int[] getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int[] vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String[] getPlayerWing() {
        return playerWing;
    }

    public void setPlayerWing(String[] playerWing) {
        this.playerWing = playerWing;
    }

    public String[] getPlayer_title() {
        return player_title;
    }

    public void setPlayer_title(String[] player_title) {
        this.player_title = player_title;
    }

    public int[] getQualifyingLevel() {
        return qualifyingLevel;
    }

    public void setQualifyingLevel(int[] qualifyingLevel) {
        this.qualifyingLevel = qualifyingLevel;
    }

    public int[] getSkillType() {
        return skillType;
    }

    public void setSkillType(int[] skillType) {
        this.skillType = skillType;
    }

    public int[] getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int[] skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int[] getZsleve() {
        return zsleve;
    }

    public void setZsleve(int[] zsleve) {
        this.zsleve = zsleve;
    }

    public boolean[] getDoubleMark() {
        return doubleMark;
    }

    public void setDoubleMark(boolean[] doubleMark) {
        this.doubleMark = doubleMark;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int[] getPlayerStar() {
        return playerStar;
    }

    public void setPlayerStar(int[] playerStar) {
        this.playerStar = playerStar;
    }

    public boolean[] getIsvip() {
        return isvip;
    }

    public void setIsvip(boolean[] isvip) {
        this.isvip = isvip;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
