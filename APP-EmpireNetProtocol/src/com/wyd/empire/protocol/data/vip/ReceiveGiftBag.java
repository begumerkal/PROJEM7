package com.wyd.empire.protocol.data.vip;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ReceiveGiftBag extends AbstractData {

    public ReceiveGiftBag(int sessionId, int serial) {
        super(Protocol.MAIN_VIP, Protocol.VIP_ReceiveGiftBag, sessionId, serial);
    }

    public ReceiveGiftBag() {
        super(Protocol.MAIN_VIP, Protocol.VIP_ReceiveGiftBag);
    }

}
