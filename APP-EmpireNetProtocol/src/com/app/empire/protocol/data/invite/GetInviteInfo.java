package com.app.empire.protocol.data.invite;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取邀请码信息
 * @author zguoqiu
 */
public class GetInviteInfo extends AbstractData {
	public GetInviteInfo(int sessionId, int serial) {
		super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteInfo, sessionId, serial);
	}

	public GetInviteInfo() {
		super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteInfo);
	}
}
