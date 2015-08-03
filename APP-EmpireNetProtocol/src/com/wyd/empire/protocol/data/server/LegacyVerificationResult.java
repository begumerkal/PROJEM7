package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 用户注册结果
 * @see AbstractData
 * @author mazheng
 */
public class LegacyVerificationResult extends AbstractData {
    private int playerId;
    private int status;//0验证成功，1验证失败
    public LegacyVerificationResult(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyVerificationResult, sessionId, serial);
    }

    public LegacyVerificationResult() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyVerificationResult);
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
