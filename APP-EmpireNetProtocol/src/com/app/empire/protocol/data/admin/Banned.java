package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 查询用户 
 * @see AbstractData
 * @author mazheng
 */
public class Banned extends AbstractData {
    private int playerId;
    private int status;//0封禁,1解封禁
    private int startTime;//封禁开始时间（单位分钟：毫秒/1000/60）
    private int endTime;//封禁结束时间（单位分钟：毫秒/1000/60）
    public Banned(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Banned, sessionId, serial);
    }

    public Banned() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Banned);
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
