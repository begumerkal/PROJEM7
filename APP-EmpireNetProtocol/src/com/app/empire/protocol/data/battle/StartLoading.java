package com.app.empire.protocol.data.battle;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类<code>StartLoading</code> 通知服务器已经开始加载数据结构
 * 
 * @author Administrator
 * 
 */
public class StartLoading extends AbstractData {
    /** 战斗id */
    private int battleId;
    
    /** 角色id */
    private int playerId;

    public StartLoading(int sessionId, int serial) {
        super(Protocol.MAIN_BATTLE, Protocol.BATTLE_StartLoading, sessionId, serial);
    }

    public StartLoading() {
        super(Protocol.MAIN_BATTLE, Protocol.BATTLE_StartLoading);
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
