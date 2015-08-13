package com.app.empire.protocol.data.cross;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类<code>StartLoading</code> 通知服务器已经开始加载数据结构
 * 
 * @author Administrator
 * 
 */
public class CrossStartLoading extends AbstractData {
    /** 战斗id */
    private int battleId;
    
    /** 角色id */
    private int playerId;

    public CrossStartLoading() {
        super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossStartLoading);
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
