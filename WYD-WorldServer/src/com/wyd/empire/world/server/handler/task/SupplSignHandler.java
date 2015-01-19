package com.wyd.empire.world.server.handler.task;

import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.SupplSignOk;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ITaskService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 补签到
 * 
 * @author zguoqiu
 */
public class SupplSignHandler implements IDataHandler {
	Logger log = Logger.getLogger(SupplSignHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			PlayerReward sign = ServiceManager.getManager().getTaskService().getService().playerRewardInfo(player.getId());
			if (sign.getSupplSigns() > 0) {
				int supplTimes = sign.getSupplTimes() + 1;
				List<Integer> supplPriceList = ServiceManager.getManager().getVersionService().getSupplPriceList();
				if (supplTimes < supplPriceList.size()) {
					int price = supplPriceList.get(supplTimes);
					if (player.getDiamond() < price) {
						throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					}
					int signDay = DateUtil.getCurrentDay();
					List<Integer> signDayList = sign.getSignDayList();
					int supplDay = signDay - 1;
					for (int i = signDayList.size() - 1; i > -1; i--) {
						if (signDay - signDayList.get(i) > 1) {
							supplDay = signDay - 1;
							break;
						} else {
							signDay = signDayList.get(i);
							supplDay = signDay - 1;
						}
					}
					if (supplDay > 0) {
						ServiceManager.getManager().getPlayerService()
								.useTicket(player, price, TradeService.ORIGIN_SUPPLSIGN, null, null, "补签到");
						String signDays = sign.getSignDays();
						if (signDays.length() < 1) {
							signDays += supplDay;
						} else {
							signDays += ("," + supplDay);
						}
						sign.updateSignDays(signDays);
						sign.setSupplTimes(sign.getSupplTimes() + 1);
						taskService.savePlayerSignInfo(player.getId());
						SupplSignOk sso = new SupplSignOk(data.getSessionId(), data.getSerial());
						sso.setSignDay(supplDay);
						session.write(sso);
					}
				}
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
