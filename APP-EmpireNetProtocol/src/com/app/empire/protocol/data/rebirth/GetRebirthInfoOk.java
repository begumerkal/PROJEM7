package com.app.empire.protocol.data.rebirth;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetRebirthInfoOk extends AbstractData {
    private int    rebirthLevel;
    private int    rebirthTopLevel;
    private int    rebirthNum;
    private int    rebirthNeedNum;
    private String rebirthRemark;
    private int    diamonds;
    private int    rebirthDiamonds;

    public GetRebirthInfoOk(int sessionId, int serial) {
        super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_GetRebirthInfoOk, sessionId, serial);
    }

    public GetRebirthInfoOk() {
        super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_GetRebirthInfoOk);
    }

    public int getRebirthLevel() {
        return rebirthLevel;
    }

    public void setRebirthLevel(int rebirthLevel) {
        this.rebirthLevel = rebirthLevel;
    }

    public int getRebirthTopLevel() {
        return rebirthTopLevel;
    }

    public void setRebirthTopLevel(int rebirthTopLevel) {
        this.rebirthTopLevel = rebirthTopLevel;
    }

    public int getRebirthNum() {
        return rebirthNum;
    }

    public void setRebirthNum(int rebirthNum) {
        this.rebirthNum = rebirthNum;
    }

    public int getRebirthNeedNum() {
        return rebirthNeedNum;
    }

    public void setRebirthNeedNum(int rebirthNeedNum) {
        this.rebirthNeedNum = rebirthNeedNum;
    }

    public String getRebirthRemark() {
        return rebirthRemark;
    }

    public void setRebirthRemark(String rebirthRemark) {
        this.rebirthRemark = rebirthRemark;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getRebirthDiamonds() {
        return rebirthDiamonds;
    }

    public void setRebirthDiamonds(int rebirthDiamonds) {
        this.rebirthDiamonds = rebirthDiamonds;
    }
}
