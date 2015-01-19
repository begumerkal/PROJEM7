package com.wyd.empire.world.server.handler.fund;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.fund.ReceiveOk;
import com.wyd.empire.world.bean.FundRecord;
import com.wyd.empire.world.bean.PlayerFund;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 领取基金
 * 
 * @author sunzx
 */
public class ReceiveHandler implements IDataHandler {
	private Logger log = Logger.getLogger(ReceiveHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
		try {
			ReceiveOk receiveOk = new ReceiveOk(data.getSessionId(), data.getSerial());
			int currentReceiveMoney = 0;// 当前可领取金额
			int receiveLevel = 0; // 已领取过最高等级
			synchronized (worldPlayer) {
				PlayerFund playerFund = ServiceManager.getManager().getPlayerFundService().getPlayerFund(worldPlayer.getId());
				if (playerFund == null && worldPlayer.getLevel() <= 20) {
					receiveOk.setResult(false);
					receiveOk.setMessage(ErrorMessages.FUND_RECEIVE_NO_MESSAGE);
					session.write(receiveOk);
					return;
				}
				List<FundRecord> fundRecordList = ServiceManager.getManager().getPlayerFundService().getFundRecord(worldPlayer.getId());
				if (fundRecordList != null && !fundRecordList.isEmpty()) {
					for (FundRecord fundRecord : fundRecordList) {
						if (receiveLevel == 0) {
							receiveLevel = fundRecord.getLevel();
						}
					}
				}
				if (playerFund != null) {
					// int zslevel =
					// worldPlayer.getBeforeRebirthLevel(worldPlayer.getPlayer().getZsLevel());
					if (playerFund.getLowFund() != null && playerFund.getLowFund() == Common.STATUS_SHOW) {
						Map<Integer, Integer> levelMap = getInfo(playerFund.getLowFundInfo());// FundUtil.getFundLevelMap(FundUtil.FUND_TYPE_LOW);
						for (java.util.Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
							if (worldPlayer.getPlayer().getLevel() >= entry.getKey() && entry.getKey() > receiveLevel) {
								currentReceiveMoney += entry.getValue();
							}
						}
					}
					if (playerFund.getMiddleFund() != null && playerFund.getMiddleFund() == Common.STATUS_SHOW) {
						Map<Integer, Integer> levelMap = getInfo(playerFund.getMiddleFundInfo());// FundUtil.getFundLevelMap(FundUtil.FUND_TYPE_MIDDLE);
						for (java.util.Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
							if (worldPlayer.getPlayer().getLevel() >= entry.getKey() && entry.getKey() > receiveLevel) {
								currentReceiveMoney += entry.getValue();
							}
						}
					}
					if (playerFund.getHighFund() != null && playerFund.getHighFund() == Common.STATUS_SHOW) {
						Map<Integer, Integer> levelMap = getInfo(playerFund.getHighFundInfo());// FundUtil.getFundLevelMap(FundUtil.FUND_TYPE_HIGH);
						for (java.util.Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
							if (worldPlayer.getPlayer().getLevel() >= entry.getKey() && entry.getKey() > receiveLevel) {
								currentReceiveMoney += entry.getValue();
							}
						}
					}
				}
				if (currentReceiveMoney > 0) {
					ServiceManager.getManager().getPlayerService()
							.addTicket(worldPlayer, currentReceiveMoney, 0, TradeService.ORIGIN_FUND_RECEIVE, 0, "", "领取基金", "", "");
					FundRecord fundRecord = new FundRecord();
					fundRecord.setPlayer(worldPlayer.getPlayer());
					fundRecord.setLevel(worldPlayer.getLevel());
					fundRecord.setMoney(currentReceiveMoney);
					fundRecord.setUpdateTime(new Date());
					ServiceManager.getManager().getPlayerFundService().save(fundRecord);
					receiveOk.setResult(true);
					receiveOk.setMessage(ErrorMessages.FUND_RECEIVE_SUCCESS_MESSAGE);
					GameLogService.getFund(worldPlayer.getId(), worldPlayer.getLevel(), currentReceiveMoney);
				} else {
					receiveOk.setResult(false);
					receiveOk.setMessage(ErrorMessages.FUND_RECEIVE_LEVEL_MESSAGE);
				}
			}
			session.write(receiveOk);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FUND_BUY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private Map<Integer, Integer> getInfo(String str) {
		Map<Integer, Integer> FundMapInfo = new LinkedHashMap<Integer, Integer>();
		String[] stra = str.split(",");
		for (String parameter : stra) {
			FundMapInfo.put(Integer.parseInt(parameter.split("=")[0]), Integer.parseInt(parameter.split("=")[1]));
		}
		return FundMapInfo;
	}

}
