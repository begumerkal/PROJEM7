package com.app.empire.protocol.data.cross;
import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.cross.CrossDate;
/**
 * 玩家冰冻结束
 * @author zgq
 */
public class CrossFrozenOver extends CrossDate {
    private int   roomId;
    private int[] playerIds;

    public CrossFrozenOver() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossFrozenOver);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int[] getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(int[] playerIds) {
        this.playerIds = playerIds;
    }
}
