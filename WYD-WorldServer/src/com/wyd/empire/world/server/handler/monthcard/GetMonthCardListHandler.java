package com.wyd.empire.world.server.handler.monthcard;

import java.util.Date;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.monthcard.GetMonthCardListOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.MonthCardService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取月卡列表
 * 
 * @author 陈杰
 * @since JDK 1.6
 */
public class GetMonthCardListHandler implements IDataHandler {
	private static Logger log = Logger.getLogger(GetMonthCardListHandler.class);

	@SuppressWarnings("static-access")
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		MonthCardService manager = ServiceManager.getManager().getMonthCardService();
		try {
			boolean isBuy = false; // 是否已经购买了月卡
			int playerCard = 0; // 玩家购买的月卡id
			boolean isCanReceive = false; // 是否可以领取返利
			int remainingDays = 0; // 月卡剩余天数
			WorldPlayer player = session.getPlayer(data.getSessionId());
			if (player.getPlayerInfo().getRemainingRemindNum() == -1 && player.getPlayerInfo().getMonthCardId() != 0
					&& player.getPlayerInfo().getBuyMonthCardTime() != null) {
				Date dueTime = DateUtil.afterNowNDay(player.getPlayerInfo().getBuyMonthCardTime(), Common.MonthCardEffectiveDaysNum);
				remainingDays = DateUtil.compareDateOnDay(dueTime, new Date());
				if (remainingDays > -1) {
					isBuy = true;
					playerCard = player.getPlayerInfo().getMonthCardId();
					if (!DateUtil.isSameDate(new Date(), player.getPlayerInfo().getLastReceiveMonthCardRebateTime())) {
						isCanReceive = true;
					}
				} else {
					if (player.getPlayerInfo().getRemainingRemindNum() < -1) {
						player.getPlayerInfo().setRemainingRemindNum(Common.RemainingRemindNum);
						player.updatePlayerInfo();
					}
				}
			}
			GetMonthCardListOk getOk = new GetMonthCardListOk(data.getSessionId(), data.getSerial());
			getOk.setCardIds(manager.getCardIds());
			getOk.setDailyRebate(manager.getDailyRebate());
			// getOk.setPurchaseAmount(manager.getPurchaseAmount());
			getOk.setBuy(isBuy);
			getOk.setPlayerCard(playerCard);
			getOk.setCanReceive(isCanReceive);
			getOk.setRemainingDays(remainingDays);
			session.write(getOk);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

}