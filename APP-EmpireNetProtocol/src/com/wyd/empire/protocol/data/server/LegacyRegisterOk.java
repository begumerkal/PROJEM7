package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 用户注册结果
 * @see AbstractData
 * @author mazheng
 */
public class LegacyRegisterOk extends AbstractData {
    private int playerId;
    private int status;//0注册成功，1帐号已存在，2系统错误
    public LegacyRegisterOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyRegisterOk, sessionId, serial);
    }

    public LegacyRegisterOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyRegisterOk);
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
