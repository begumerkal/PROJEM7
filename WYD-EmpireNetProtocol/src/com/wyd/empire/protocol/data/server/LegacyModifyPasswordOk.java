package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 用户注册结果
 * @see AbstractData
 * @author mazheng
 */
public class LegacyModifyPasswordOk extends AbstractData {
    private int playerId;
    private int status;//0修改密码成功，1旧密码错误，2系统错误
    public LegacyModifyPasswordOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyModifyPasswordOk, sessionId, serial);
    }

    public LegacyModifyPasswordOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyModifyPasswordOk);
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
}
