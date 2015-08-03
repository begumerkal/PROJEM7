package com.wyd.empire.protocol.data.account;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 修改密码成功
 * @see AbstractData
 * @author mazheng
 */
public class ModifyPasswordOk extends AbstractData {
    public ModifyPasswordOk(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ModifyPasswordOk, sessionId, serial);
    }

    public ModifyPasswordOk() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ModifyPasswordOk);
    }
}
