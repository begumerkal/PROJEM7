package com.wyd.empire.protocol.data.cross;
import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.cross.CrossDate;
public class CrossUpdateMedal extends CrossDate {
    private int   roomId;
    private int   battleId;
    private int   campCount;
    private int[] campId;
    private int[] campMedalNum;

    public CrossUpdateMedal() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossUpdateMedal);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getCampCount() {
        return campCount;
    }

    public void setCampCount(int campCount) {
        this.campCount = campCount;
    }

    public int[] getCampId() {
        return campId;
    }

    public void setCampId(int[] campId) {
        this.campId = campId;
    }

    public int[] getCampMedalNum() {
        return campMedalNum;
    }

    public void setCampMedalNum(int[] campMedalNum) {
        this.campMedalNum = campMedalNum;
    }
}
