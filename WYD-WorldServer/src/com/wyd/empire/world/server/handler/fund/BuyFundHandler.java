package com.wyd.empire.world.server.handler.fund;

import java.util.Date;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.fund.BuyFund;
import com.wyd.empire.protocol.data.fund.BuyFundOk;
import com.wyd.empire.world.bean.PlayerFund;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.FundUtil;
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
 * 购买基金
 * 
 * @author sunzx
 */
public class BuyFundHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetFundInfoHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
		BuyFund buyFund = (BuyFund) data;
		try {
			if (null == worldPlayer)
				return;
			if (worldPlayer.getLevel() > 20) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			int useAmount;
			if (buyFund.getFundType() == FundUtil.FUND_TYPE_LOW) {
				useAmount = FundUtil.FUND_TYPE_LOW_AMOUNT;
			} else if (buyFund.getFundType() == FundUtil.FUND_TYPE_MIDDLE) {
				useAmount = FundUtil.FUND_TYPE_MIDDLE_AMOUNT;
			} else {
				useAmount = FundUtil.FUND_TYPE_HIGH_AMOUNT;
			}
			BuyFundOk buyFundOk = new BuyFundOk(data.getSessionId(), data.getSerial());
			if (worldPlayer.getPlayer().getAmount() < useAmount) {
				buyFundOk.setResult(false);
				buyFundOk.setMessage(ErrorMessages.FUND_BUY_FAIL);
			} else {
				// 如果玩家未充值过则不允许购买基金
				if (ServiceManager.getManager().getPlayerBillService().playerIsFirstCharge(worldPlayer.getPlayer())) {
					throw new ProtocolException(ErrorMessages.FUND_BUY_FAILL, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				PlayerFund playerFund = ServiceManager.getManager().getPlayerFundService().getPlayerFund(worldPlayer.getId());
				if (playerFund == null) {
					playerFund = new PlayerFund();
				} else {
					throw new ProtocolException(ErrorMessages.FUND_RECEIVE_AGAIN_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				ServiceManager.getManager().getPlayerService()
						.useTicket(worldPlayer, useAmount, TradeService.ORIGIN_FUND_BUY, null, null, "基金：" + useAmount);
				if (buyFund.getFundType() == FundUtil.FUND_TYPE_LOW) {
					playerFund.setLowFund((byte) Common.STATUS_SHOW);
				} else if (buyFund.getFundType() == FundUtil.FUND_TYPE_MIDDLE) {
					playerFund.setMiddleFund((byte) Common.STATUS_SHOW);
				} else {
					playerFund.setHighFund((byte) Common.STATUS_SHOW);
				}
				playerFund.setPlayer(worldPlayer.getPlayer());
				playerFund.setUpdateTime(new Date());
				if (playerFund.getId() == null) {
					ServiceManager.getManager().getPlayerFundService().save(playerFund);
				} else {
					ServiceManager.getManager().getPlayerFundService().update(playerFund);
				}
				buyFundOk.setResult(true);
				buyFundOk.setMessage(ErrorMessages.FUND_BUY_SUCCESS);
				worldPlayer.updateButtonInfo(Common.BUTTON_ID_FUND, false, 30);
				GameLogService.buyFund(worldPlayer.getId(), worldPlayer.getLevel(), buyFund.getFundType(), useAmount);
			}
			session.write(buyFundOk);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FUND_BUY_FAIL, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
