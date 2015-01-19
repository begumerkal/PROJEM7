package com.wyd.empire.protocol.data.reward;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetReward extends AbstractData {
    private int rewardType; // 0领取首冲奖励、1抽奖奖励
 

    public GetReward(int sessionId, int serial) {
        super(Protocol.MAIN_REWARD, Protocol.REWARD_GetReward, sessionId, serial);
    }

    public GetReward() {
        super(Protocol.MAIN_REWARD, Protocol.REWARD_GetReward);
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    
    
}
