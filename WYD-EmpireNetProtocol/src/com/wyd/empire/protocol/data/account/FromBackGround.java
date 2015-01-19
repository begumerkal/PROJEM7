package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>LoginOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ACCOUNT下子命令ACCOUNT_LoginOk(账户登录成功)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class FromBackGround extends AbstractData {
    public FromBackGround(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_FromBackGround, sessionId, serial);
    }

    public FromBackGround() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_FromBackGround);
    }
}
