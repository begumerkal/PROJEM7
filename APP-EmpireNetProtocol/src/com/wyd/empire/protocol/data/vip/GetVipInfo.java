package com.wyd.empire.protocol.data.vip;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 */
public class GetVipInfo extends AbstractData {

    public GetVipInfo(int sessionId, int serial) {
        super(Protocol.MAIN_VIP, Protocol.VIP_GetVipInfo, sessionId, serial);
    }

    public GetVipInfo() {
        super(Protocol.MAIN_VIP, Protocol.VIP_GetVipInfo);
    }

}
