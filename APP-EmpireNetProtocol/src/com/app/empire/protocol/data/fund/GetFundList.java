package com.app.empire.protocol.data.fund;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取基金信息列表
 * @author sunzx
 *
 */
public class GetFundList extends AbstractData {
	public GetFundList(int sessionId, int serial) {
		super(Protocol.MAIN_FUND, Protocol.FUND_GetFundList, sessionId, serial);
	}

	public GetFundList() {
		super(Protocol.MAIN_FUND, Protocol.FUND_GetFundList);
	}
}
