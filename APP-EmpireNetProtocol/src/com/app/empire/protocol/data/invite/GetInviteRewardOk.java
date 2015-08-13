package com.app.empire.protocol.data.invite;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 玩家领取奖励
 * @author zguoqiu
 */
public class GetInviteRewardOk extends AbstractData {
	public GetInviteRewardOk(int sessionId, int serial) {
		super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteRewardOk, sessionId, serial);
	}

	public GetInviteRewardOk() {
		super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteRewardOk);
	}
}
