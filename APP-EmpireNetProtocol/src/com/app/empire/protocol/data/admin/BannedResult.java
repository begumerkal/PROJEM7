package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 封禁用户结果
 * @see AbstractData
 * @author mazheng
 */
public class BannedResult extends AbstractData {
    private boolean success;
    public BannedResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_BannedResult, sessionId, serial);
    }

    public BannedResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_BannedResult);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
