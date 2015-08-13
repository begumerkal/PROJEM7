package com.app.empire.protocol.data.strengthen;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class ChangeAttribute extends AbstractData {
    private int firstItemid;
    private int secondItemId;

    public ChangeAttribute(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_ChangeAttribute, sessionId, serial);
    }

    public ChangeAttribute() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_ChangeAttribute);
    }

    public int getFirstItemid() {
        return firstItemid;
    }

    public void setFirstItemid(int firstItemid) {
        this.firstItemid = firstItemid;
    }

    public int getSecondItemId() {
        return secondItemId;
    }

    public void setSecondItemId(int secondItemId) {
        this.secondItemId = secondItemId;
    }
}
