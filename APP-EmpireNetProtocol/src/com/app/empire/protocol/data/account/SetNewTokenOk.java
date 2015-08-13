package com.app.empire.protocol.data.account;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>Login</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ACCOUNT下子命令ACCOUNT_Login(账户登录)对应数据封装。
 * 
 * @see AbstractData
 * @author chenjie
 */
public class SetNewTokenOk extends AbstractData {
	
    public SetNewTokenOk(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_SetNewTokenOk, sessionId, serial);
    }

    public SetNewTokenOk() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_SetNewTokenOk);
    }
}
