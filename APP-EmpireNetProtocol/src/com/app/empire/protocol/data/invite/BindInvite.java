package com.app.empire.protocol.data.invite;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 玩家绑定邀请码
 * 
 * @see AbstractData
 * @author zguoqiu
 */
public class BindInvite extends AbstractData {
    private String inviteCode;

    public BindInvite(int sessionId, int serial) {
        super(Protocol.MAIN_INVITE, Protocol.INVITE_BindInvite, sessionId, serial);
    }

    public BindInvite() {
        super(Protocol.MAIN_INVITE, Protocol.INVITE_BindInvite);
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
