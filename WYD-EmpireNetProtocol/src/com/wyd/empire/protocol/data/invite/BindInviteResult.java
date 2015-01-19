package com.wyd.empire.protocol.data.invite;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 玩家绑定邀请码的结果
 * 
 * @see AbstractData
 * @author zguoqiu
 */
public class BindInviteResult extends AbstractData {
    private int status;

    public BindInviteResult(int sessionId, int serial) {
        super(Protocol.MAIN_INVITE, Protocol.INVITE_BindInviteResult, sessionId, serial);
    }

    public BindInviteResult() {
        super(Protocol.MAIN_INVITE, Protocol.INVITE_BindInviteResult);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
