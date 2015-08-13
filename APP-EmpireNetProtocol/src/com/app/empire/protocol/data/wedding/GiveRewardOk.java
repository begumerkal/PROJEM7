package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GiveRewardOk extends AbstractData {

    public GiveRewardOk(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GiveRewardOk, sessionId, serial);
    }

    public GiveRewardOk() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GiveRewardOk);
    }

}
