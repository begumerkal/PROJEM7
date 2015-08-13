package com.app.empire.protocol.data.cross;
import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;
public class CrossOtherSkillEquip extends CrossDate {
    private int   roomId;
    private int   battleId;
    private int   currentPlayerId;
    private int   itemcount;
    private int[] itmeIds;

    public CrossOtherSkillEquip() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossOtherSkillEquip);
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

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public int getItemcount() {
        return itemcount;
    }

    public void setItemcount(int itemcount) {
        this.itemcount = itemcount;
    }

    public int[] getItmeIds() {
        return itmeIds;
    }

    public void setItmeIds(int[] itmeIds) {
        this.itmeIds = itmeIds;
    }
}
