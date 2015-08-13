package com.app.empire.protocol.data.vip;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
