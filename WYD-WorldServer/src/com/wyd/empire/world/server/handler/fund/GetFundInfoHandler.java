package com.wyd.empire.world.server.handler.fund;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.fund.GetFundInfoOk;
import com.wyd.empire.world.bean.FundRecord;
import com.wyd.empire.world.bean.PlayerFund;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.FundUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家获取基金信息
 * 
 * @author sunzx
 */
public class GetFundInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetFundInfoHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
		try {
			int[] lowValue = new int[2]; // 初级基金投入收获数值
			int[] middleValue = new int[2]; // 中级基金投入收获数值
			int[] highValue = new int[2]; // 高级基金投入收获数值
			boolean lowFundStatus = false; // 初级基金购买按钮状态
			boolean middleFundStatus = false; // 中级基金购买按钮状态
			boolean highFundStatus = false; // 高级基金购买按钮状态
			boolean receiveStatus = true; // 领取基金返利按钮状态g
			int nextReceiveLevel = 0; // 下次领取等级
			int nextReceiveMoney = 0; // 下次领取金额
			int pileReceiveMoney = 0; // 累积领取金额
			int currentReceiveMoney = 0;// 当前可领取金额
			int receiveLevel = 0; // 已领取过最高等级
			int status = 0; // 基金状态（0是未购买，1是已购买未到领取时间，2是可以领取,3领完）
			lowValue[0] = 100;
			lowValue[1] = 2000;
			middleValue[0] = 500;
			middleValue[1] = 15000;
			highValue[0] = 1000;
			highValue[1] = 50000;
			List<FundRecord> fundRecordList = ServiceManager.getManager().getPlayerFundService().getFundRecord(worldPlayer.getId());
			if (fundRecordList != null && !fundRecordList.isEmpty()) {
				for (FundRecord fundRecord : fundRecordList) {
					pileReceiveMoney += fundRecord.getMoney();
					if (receiveLevel == 0) {
						receiveLevel = fundRecord.getLevel();
					}
				}
			}
			PlayerFund playerFund = ServiceManager.getManager().getPlayerFundService().getPlayerFund(worldPlayer.getId());
			if (worldPlayer.getPlayer().getLevel() <= 20) {
				if (playerFund == null) {
					lowFundStatus = true;
					middleFundStatus = true;
					highFundStatus = true;
				}
			} else {
				if (playerFund == null) {
					receiveStatus = false;
				}
			}
			if (playerFund != null) {
				if (playerFund.getLowFund() != null && playerFund.getLowFund() == Common.STATUS_SHOW) {
					Map<Integer, Integer> levelMap = FundUtil.getFundLevelMap(FundUtil.FUND_TYPE_LOW);
					for (java.util.Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
						if (receiveLevel < entry.getKey() && worldPlayer.getPlayer().getLevel() < entry.getKey()) {
							nextReceiveLevel = entry.getKey();
							nextReceiveMoney = entry.getValue();
							break;
						}
					}
					for (java.util.Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
						if (worldPlayer.getPlayer().getLevel() >= entry.getKey() && entry.getKey() > receiveLevel) {
							currentReceiveMoney += entry.getValue();
						}
					}
				}
				if (playerFund.getMiddleFund() != null && playerFund.getMiddleFund() == Common.STATUS_SHOW) {
					Map<Integer, Integer> levelMap = FundUtil.getFundLevelMap(FundUtil.FUND_TYPE_MIDDLE);
					for (java.util.Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
						if (receiveLevel < entry.getKey() && worldPlayer.getPlayer().getLevel() < entry.getKey()) {
							if (nextReceiveLevel == 0) {
								nextReceiveLevel = entry.getKey();
								nextReceiveMoney = entry.getValue();
							} else if (nextReceiveLevel == entry.getKey()) {
								nextReceiveMoney += entry.getValue();
							}
							break;
						}
					}
					for (java.util.Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
						if (worldPlayer.getPlayer().getLevel() >= entry.getKey() && entry.getKey() > receiveLevel) {
							currentReceiveMoney += entry.getValue();
						}
					}
				}
				if (playerFund.getHighFund() != null && playerFund.getHighFund() == Common.STATUS_SHOW) {
					Map<Integer, Integer> levelMap = FundUtil.getFundLevelMap(FundUtil.FUND_TYPE_HIGH);
					for (java.util.Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
						if (receiveLevel < entry.getKey() && worldPlayer.getPlayer().getLevel() < entry.getKey()) {
							if (nextReceiveLevel == 0) {
								nextReceiveLevel = entry.getKey();
								nextReceiveMoney = entry.getValue();
							} else if (nextReceiveLevel == entry.getKey()) {
								nextReceiveMoney += entry.getValue();
							}
							break;
						}
					}
					for (java.util.Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
						if (worldPlayer.getPlayer().getLevel() >= entry.getKey() && entry.getKey() > receiveLevel) {
							currentReceiveMoney += entry.getValue();
						}
					}
				}
			}
			if (receiveLevel == 90 || currentReceiveMoney == 0) {
				receiveStatus = false;
				if (nextReceiveLevel > worldPlayer.getLevel()) {
					status = 1;
				}
				if (pileReceiveMoney > 0 && nextReceiveLevel == 0) {
					status = 3;
				}
			} else {
				status = 2;
			}
			GetFundInfoOk getFundInfoOk = new GetFundInfoOk(data.getSessionId(), data.getSerial());
			getFundInfoOk.setLowValue(lowValue);
			getFundInfoOk.setMiddleValue(middleValue);
			getFundInfoOk.setHighValue(highValue);
			getFundInfoOk.setLowFundStatus(lowFundStatus);
			getFundInfoOk.setMiddleFundStatus(middleFundStatus);
			getFundInfoOk.setHighFundStatus(highFundStatus);
			getFundInfoOk.setNextReceiveLevel(nextReceiveLevel);
			getFundInfoOk.setNextReceiveMoney(nextReceiveMoney);
			getFundInfoOk.setPileReceiveMoney(pileReceiveMoney);
			getFundInfoOk.setCurrentReceiveMoney(currentReceiveMoney);
			getFundInfoOk.setReceiveStatus(receiveStatus);
			getFundInfoOk.setLevelLimit(20);
			getFundInfoOk.setStatus(status);
			session.write(getFundInfoOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FUND_INFO_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
