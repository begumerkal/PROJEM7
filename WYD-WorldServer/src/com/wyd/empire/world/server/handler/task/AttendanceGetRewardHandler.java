package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.AttendanceGetReward;
import com.wyd.empire.protocol.data.task.AttendanceGetRewardOk;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.bean.Reward;
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
 * 领取累计签到奖励
 * 
 * @author Administrator
 */
public class AttendanceGetRewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(AttendanceGetRewardHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		AttendanceGetReward getReward = (AttendanceGetReward) data;
		try {
			int totalDays = getReward.getTotalDays();
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			PlayerReward sign = taskService.playerRewardInfo(player.getId());
			Reward signReward = taskService.getRewardByParam(totalDays, 0);
			if (null == signReward)
				return;
			if (!sign.isSignReward(totalDays) && signReward.getParam() == totalDays) {
				String totalRewards = sign.getTotalRewards();
				if (totalRewards.length() < 1) {
					totalRewards += totalDays;
				} else {
					totalRewards += ("," + totalDays);
				}
				sign.updateTotalRewards(totalRewards);
				taskService.savePlayerSignInfo(player.getId());
				List<RewardInfo> riList = taskService.sendSignReward(signReward.getReward(), player, 7, TradeService.ORIGIN_SIGN, "累计签到");
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
				StringBuffer items = new StringBuffer();
				items.append("(3:");
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
