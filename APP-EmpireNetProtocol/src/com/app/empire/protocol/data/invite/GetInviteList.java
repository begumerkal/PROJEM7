package com.app.empire.protocol.data.invite;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取邀请成功的玩家名单
 * @author zguoqiu
 */
public class GetInviteList extends AbstractData {
    private int      pageIndex;
	public GetInviteList(int sessionId, int serial) {
		super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteList, sessionId, serial);
	}

	public GetInviteList() {
		super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteList);
	}

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
