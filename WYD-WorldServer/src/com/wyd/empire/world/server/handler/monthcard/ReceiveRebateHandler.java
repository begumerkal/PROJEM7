package com.wyd.empire.world.server.handler.monthcard;

import java.util.Date;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.monthcard.ReceiveRebateOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.MonthCardService;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 领取月卡每日返利
 * 
 * @since JDK 1.6
 */
public class ReceiveRebateHandler implements IDataHandler {
	private static Logger log = Logger.getLogger(ReceiveRebateHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		MonthCardService manager = ServiceManager.getManager().getMonthCardService();
		try {
			WorldPlayer player = session.getPlayer(data.getSessionId());
			// 非正常玩家
			if (player.getPlayerInfo().getRemainingRemindNum() != -1 || player.getPlayerInfo().getMonthCardId() == 0
					|| player.getPlayerInfo().getBuyMonthCardTime() == null) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			Date dueTime = DateUtil.afterNowNDay(player.getPlayerInfo().getBuyMonthCardTime(), Common.MonthCardEffectiveDaysNum);
			int remainingDays = DateUtil.compareDateOnDay(dueTime, new Date());
			int receiveRebateNumber = 0; // 成功领取返利钻石数
			if (remainingDays > -1) {
				if (player.getPlayerInfo().getLastReceiveMonthCardRebateTime() == null
						|| !DateUtil.isSameDate(player.getPlayerInfo().getLastReceiveMonthCardRebateTime(), new Date())) {
					player.getPlayerInfo().setLastReceiveMonthCardRebateTime(new Date());
					player.updatePlayerInfo();
					receiveRebateNumber = manager.getMonthCardById(player.getPlayerInfo().getMonthCardId()).getDailyRebate();
					ServiceManager.getManager().getPlayerService()
							.addTicket(player, receiveRebateNumber, 0, TradeService.ORIGIN_RECEIVE_REBATE, 0f, "", "月卡每日返利获得", "", "");
				}
			} else {
				player.getPlayerInfo().setRemainingRemindNum(Common.RemainingRemindNum);
				player.updatePlayerInfo();
				throw new ProtocolException(ErrorMessages.PLAYER_TAKEONOVERTIME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			ReceiveRebateOk receiveRebateOk = new ReceiveRebateOk(data.getSessionId(), data.getSerial());
			receiveRebateOk.setReceiveRebateNumber(receiveRebateNumber);
			session.write(receiveRebateOk);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

}