package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 查询用户 
 * @see AbstractData
 * @author mazheng
 */
public class Gag extends AbstractData {
    private int playerId;
    private int status;//禁言类型  1允许所有频道聊天,2禁止公共频道聊天,3禁止所有频道聊天
    private int endTime;//封禁结束时间（单位分钟：毫秒/1000/60）
    public Gag(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Gag, sessionId, serial);
    }

    public Gag() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Gag);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
