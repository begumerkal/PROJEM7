package com.wyd.empire.protocol.data.fund;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetFundDetail extends AbstractData {
    
    public GetFundDetail(int sessionId, int serial) {
        super(Protocol.MAIN_FUND, Protocol.FUND_GetFundDetail, sessionId, serial);
    }

    public GetFundDetail() {
        super(Protocol.MAIN_FUND, Protocol.FUND_GetFundDetail);
    }
}
