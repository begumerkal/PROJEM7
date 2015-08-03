package com.wyd.empire.protocol.data.bossmaproom;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetRoomListOk extends AbstractData {
    private int       roomCount;
    private int[]     roomId;
    private String[]  roomName;
    private int[]     battleStatus;
    private int[]     playerCountNum;
    private String[]  passWord;
    private int[]     playerNum;
    private boolean[] roomStaus;
    private int[]     mapId;
    private int[]     roomStar;

    public GetRoomListOk(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetRoomListOk, sessionId, serial);
    }

    public GetRoomListOk() {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_GetRoomListOk);
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int[] getRoomId() {
        return roomId;
    }

    public void setRoomId(int[] roomId) {
        this.roomId = roomId;
    }

    public String[] getRoomName() {
        return roomName;
    }

    public void setRoomName(String[] roomName) {
        this.roomName = roomName;
    }

    public int[] getPlayerCountNum() {
        return playerCountNum;
    }

    public void setPlayerCountNum(int[] playerCountNum) {
        this.playerCountNum = playerCountNum;
    }

    public String[] getPassWord() {
        return passWord;
    }

    public void setPassWord(String[] passWord) {
        this.passWord = passWord;
    }

    public int[] getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int[] playerNum) {
        this.playerNum = playerNum;
    }

    public boolean[] getRoomStaus() {
        return roomStaus;
    }

    public void setRoomStaus(boolean[] roomStaus) {
        this.roomStaus = roomStaus;
    }

    public int[] getBattleStatus() {
        return battleStatus;
    }

    public void setBattleStatus(int[] battleStatus) {
        this.battleStatus = battleStatus;
    }

    public int[] getMapId() {
        return mapId;
    }

    public void setMapId(int[] mapId) {
        this.mapId = mapId;
    }

    public int[] getRoomStar() {
        return roomStar;
    }

    public void setRoomStar(int[] roomStar) {
        this.roomStar = roomStar;
    }
}
