package com.app.empire.protocol.data.invite;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 玩家领取奖励
 * @author zguoqiu
 */
public class GetInviteReward extends AbstractData {
	public GetInviteReward(int sessionId, int serial) {
		super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteReward, sessionId, serial);
	}

	public GetInviteReward() {
		super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteReward);
	}
}
