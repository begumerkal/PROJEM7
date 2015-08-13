package com.app.empire.protocol.data.reward;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetRewardList extends AbstractData {
    public GetRewardList(int sessionId, int serial) {
        super(Protocol.MAIN_REWARD, Protocol.REWARD_GetRewardList, sessionId, serial);
    }

    public GetRewardList() {
        super(Protocol.MAIN_REWARD, Protocol.REWARD_GetRewardList);
    }
}
