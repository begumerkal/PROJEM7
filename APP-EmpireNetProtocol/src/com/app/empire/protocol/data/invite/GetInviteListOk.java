package com.app.empire.protocol.data.invite;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取邀请码信息
 * 
 * @author zguoqiu
 */
public class GetInviteListOk extends AbstractData {
    private String[] serviceName;
    private String[] playerName;
    private int      pageIndex;
    private int      pageCount;

    public GetInviteListOk(int sessionId, int serial) {
        super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteListOk, sessionId, serial);
    }

    public GetInviteListOk() {
        super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteListOk);
    }

    public String[] getServiceName() {
        return serviceName;
    }

    public void setServiceName(String[] serviceName) {
        this.serviceName = serviceName;
    }

    public String[] getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String[] playerName) {
        this.playerName = playerName;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
