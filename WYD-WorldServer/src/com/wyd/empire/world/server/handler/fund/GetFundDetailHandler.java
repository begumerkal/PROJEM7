package com.wyd.empire.world.server.handler.fund;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.fund.GetFundDetailOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.FundUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家获取基金信息
 * 
 * @author sunzx
 */
public class GetFundDetailHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetFundDetailHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {

			Map<Integer, Integer> lowMap = FundUtil.getFundLevelMap(FundUtil.FUND_TYPE_LOW);
			int[] lowLevel = new int[lowMap.entrySet().size()];
			int[] lowValue = new int[lowMap.entrySet().size()]; // 初级基金投入收获数值
			int index = 0;
			for (java.util.Map.Entry<Integer, Integer> entry : lowMap.entrySet()) {
				lowLevel[index] = entry.getKey();
				lowValue[index] = entry.getValue();
				index++;
			}

			Map<Integer, Integer> midMap = FundUtil.getFundLevelMap(FundUtil.FUND_TYPE_MIDDLE);
			int[] midLevel = new int[midMap.entrySet().size()];
			int[] midValue = new int[midMap.entrySet().size()]; // 中级基金投入收获数值
			index = 0;
			for (java.util.Map.Entry<Integer, Integer> entry : midMap.entrySet()) {
				midLevel[index] = entry.getKey();
				midValue[index] = entry.getValue();
				index++;
			}

			Map<Integer, Integer> highMap = FundUtil.getFundLevelMap(FundUtil.FUND_TYPE_HIGH);
			int[] highLevel = new int[highMap.entrySet().size()];
			int[] highValue = new int[highMap.entrySet().size()]; // 中级基金投入收获数值
			index = 0;
			for (java.util.Map.Entry<Integer, Integer> entry : highMap.entrySet()) {
				highLevel[index] = entry.getKey();
				highValue[index] = entry.getValue();
				index++;
			}

			GetFundDetailOk getFundDetailOk = new GetFundDetailOk(data.getSessionId(), data.getSerial());
			getFundDetailOk.setHighLevel(highLevel);
			getFundDetailOk.setHighValue(highValue);
			getFundDetailOk.setLowLevel(lowLevel);
			getFundDetailOk.setLowValue(lowValue);
			getFundDetailOk.setMidLevel(midLevel);
			getFundDetailOk.setMidValue(midValue);

			session.write(getFundDetailOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FUND_INFO_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
