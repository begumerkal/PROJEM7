package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
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
