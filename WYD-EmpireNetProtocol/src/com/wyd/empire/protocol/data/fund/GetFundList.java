package com.wyd.empire.protocol.data.fund;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
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
