package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>Login</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ADMIN下子命令ADMIN_UpdateAccountInfo更新account信息。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class UpdateAccountResultOk extends AbstractData {
    private String content; // json格式

    public UpdateAccountResultOk(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateAccountResultOk, sessionId, serial);
    }

    public UpdateAccountResultOk() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateAccountResultOk);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
