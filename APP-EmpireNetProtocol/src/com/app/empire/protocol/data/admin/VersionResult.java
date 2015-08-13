package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 版本管理成功
 * 
 * @see AbstractData
 * @author mazheng
 */
public class VersionResult extends AbstractData {
    private boolean success;

    public VersionResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_VersionResult, sessionId, serial);
    }

    public VersionResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_VersionResult);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
