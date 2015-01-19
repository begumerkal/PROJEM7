package com.wyd.empire.protocol.data.task;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetRewardNum extends AbstractData {
    private int signRewardNum;
    private int loginRewardNum;
    private int levelRewardNum;
    private int onlineRewardNum;

    public GetRewardNum(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetRewardNum, sessionId, serial);
    }

    public GetRewardNum() {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetRewardNum);
    }

    public int getSignRewardNum() {
        return signRewardNum;
    }

    public void setSignRewardNum(int signRewardNum) {
        this.signRewardNum = signRewardNum;
    }

    public int getLoginRewardNum() {
        return loginRewardNum;
    }

    public void setLoginRewardNum(int loginRewardNum) {
        this.loginRewardNum = loginRewardNum;
    }

    public int getLevelRewardNum() {
        return levelRewardNum;
    }

    public void setLevelRewardNum(int levelRewardNum) {
        this.levelRewardNum = levelRewardNum;
    }

    public int getOnlineRewardNum() {
        return onlineRewardNum;
    }

    public void setOnlineRewardNum(int onlineRewardNum) {
        this.onlineRewardNum = onlineRewardNum;
    }
}
