package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 禁言结果
 * @see AbstractData
 * @author mazheng
 */
public class GagResult extends AbstractData {
    private boolean success;
    public GagResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GagResult, sessionId, serial);
    }

    public GagResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GagResult);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
