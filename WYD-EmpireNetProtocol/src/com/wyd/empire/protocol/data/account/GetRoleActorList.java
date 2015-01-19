package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class GetRoleActorList extends AbstractData {
    public GetRoleActorList(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetRoleActorList, sessionId, serial);
    }

    public GetRoleActorList() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetRoleActorList);
    }
}
