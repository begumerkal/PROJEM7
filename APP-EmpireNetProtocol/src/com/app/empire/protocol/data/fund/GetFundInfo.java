package com.app.empire.protocol.data.fund;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取基金信息
 * @author sunzx
 *
 */
public class GetFundInfo extends AbstractData {
	public GetFundInfo(int sessionId, int serial) {
		super(Protocol.MAIN_FUND, Protocol.FUND_GetFundInfo, sessionId, serial);
	}

	public GetFundInfo() {
		super(Protocol.MAIN_FUND, Protocol.FUND_GetFundInfo);
	}
}
