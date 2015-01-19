package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 封禁用户结果
 * @see AbstractData
 * @author mazheng
 */
public class UpdateResult extends AbstractData {
    private boolean success;
    public UpdateResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateResult, sessionId, serial);
    }

    public UpdateResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateResult);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
