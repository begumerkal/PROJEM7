package com.wyd.empire.protocol.data.invite;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
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
