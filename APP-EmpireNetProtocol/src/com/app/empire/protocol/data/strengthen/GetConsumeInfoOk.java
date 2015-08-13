package com.app.empire.protocol.data.strengthen;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetConsumeInfoOk extends AbstractData {
    private int gold;
    private int successRate;

    public GetConsumeInfoOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetConsumeInfoOk, sessionId, serial);
    }

    public GetConsumeInfoOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetConsumeInfoOk);
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(int successRate) {
        this.successRate = successRate;
    }
}
