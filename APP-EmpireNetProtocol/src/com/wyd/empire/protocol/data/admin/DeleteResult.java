package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 数据删除结果
 * @see AbstractData
 * @author mazheng
 */
public class DeleteResult extends AbstractData {
    private boolean success;
    public DeleteResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_DeleteResult, sessionId, serial);
    }

    public DeleteResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_DeleteResult);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
