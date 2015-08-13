package com.app.empire.protocol.data.server;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 更新个人信息（完善资料）
 * 
 * @see AbstractData
 * @author mazheng
 */
public class UpdateAccountInfoOk extends AbstractData {
    private String content; // json格式

    public UpdateAccountInfoOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_UpdateAccountInfoOk, sessionId, serial);
    }

    public UpdateAccountInfoOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_UpdateAccountInfoOk);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
