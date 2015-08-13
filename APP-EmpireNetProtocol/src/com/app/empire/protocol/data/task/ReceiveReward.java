package com.app.empire.protocol.data.task;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class ReceiveReward extends AbstractData {
    private int rewardType; // 奖励类型 2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励,6在线奖励,7在线抽奖奖励
    private int param;      // 奖励参数

    public ReceiveReward(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_ReceiveReward, sessionId, serial);
    }

    public ReceiveReward() {
        super(Protocol.MAIN_TASK, Protocol.TASK_ReceiveReward);
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }
}
