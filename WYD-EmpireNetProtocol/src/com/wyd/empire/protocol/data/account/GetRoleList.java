package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class GetRoleList extends AbstractData {
    public GetRoleList(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetRoleList, sessionId, serial);
    }

    public GetRoleList() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetRoleList);
    }
}
