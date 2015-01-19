package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 邮件发送结果
 * @see AbstractData
 * @author mazheng
 */
public class SendMailResult extends AbstractData {
    private boolean success;
    public SendMailResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_SendMailResult, sessionId, serial);
    }

    public SendMailResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_SendMailResult);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
