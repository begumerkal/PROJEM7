package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 更新结果
 * @see AbstractData
 * @author mazheng
 */
public class UpdateByJsonResult extends AbstractData {
    private boolean success;
    public UpdateByJsonResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateByJsonResult, sessionId, serial);
    }

    public UpdateByJsonResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateByJsonResult);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
