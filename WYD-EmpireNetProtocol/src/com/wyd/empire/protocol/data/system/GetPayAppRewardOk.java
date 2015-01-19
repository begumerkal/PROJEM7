package com.wyd.empire.protocol.data.system;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 领取付费包奖励
 * @author zengxc
 *
 */
public class GetPayAppRewardOk extends AbstractData {

    public GetPayAppRewardOk(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetPayAppRewardOk, sessionId, serial);
    }

    public GetPayAppRewardOk() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetPayAppRewardOk);
    }


}
