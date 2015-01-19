package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.AttendanceGetRewardOk;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ITaskService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 签到
 * 
 * @author zguoqiu
 */
public class SignHandler implements IDataHandler {
	Logger log = Logger.getLogger(SignHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			PlayerReward sign = ServiceManager.getManager().getTaskService().getService().playerRewardInfo(player.getId());
			if (!sign.isSign(DateUtil.getCurrentDay())) {
				String signDays = sign.getSignDays();
				if (signDays.length() < 1) {
					signDays += DateUtil.getCurrentDay();
				} else {
					signDays += ("," + DateUtil.getCurrentDay());
				}
				sign.updateSignDays(signDays);
				taskService.savePlayerSignInfo(player.getId());
				Reward signReward = taskService.getRewardByParam(sign.getArraySigns(), 1);
				if (null == signReward) {
					List<Reward> rewardList = taskService.getRewardList(1);
					if (rewardList.size() > 0) {
						signReward = rewardList.get(rewardList.size() - 1);
					}
				}
				List<RewardInfo> riList = null;
				if (null != signReward && signReward.getParam() <= sign.getArraySigns()) {
					riList = taskService.sendSignReward(signReward.getReward(), player, 7, TradeService.ORIGIN_SIGN, "连续签到");
				}
				List<Integer> rewardItems = new ArrayList<Integer>();
				List<Integer> rewardCount = new ArrayList<Integer>();
				if (riList != null) {
					for (RewardInfo ri : riList) {
						rewardItems.add(ri.getItemId());
						rewardCount.add(ri.getCount());
					}
				}
				AttendanceGetRewardOk agro = new AttendanceGetRewardOk(data.getSessionId(), data.getSerial());
				agro.setRewardItems(ServiceUtils.getInts(rewardItems.toArray()));
				agro.setRewardCount(ServiceUtils.getInts(rewardCount.toArray()));
				session.write(agro);
				ServiceManager.getManager().getTaskService().qd(player);
				player.updateButtonInfo(Common.BUTTON_ID_REG, false, 30);
				StringBuffer items = new StringBuffer();
				items.append("(2:");
				items.append(signReward.getReward());
				items.append(")");
				GameLogService.regis(player.getId(), player.getLevel(), sign.getArraySigns(), sign.getSignDayList().size(),
						items.toString());
			} else {
				throw new ProtocolException(ErrorMessages.TASK_EVERYDAYGOT_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
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
