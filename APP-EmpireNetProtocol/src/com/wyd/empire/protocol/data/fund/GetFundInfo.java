package com.wyd.empire.protocol.data.fund;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
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
