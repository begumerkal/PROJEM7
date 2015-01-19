package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.ReceiveReward;
import com.wyd.empire.protocol.data.task.ReceiveRewardOk;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
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
 * 领取奖励
 * 
 * @author zguoqiu
 */
public class ReceiveRewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(ReceiveRewardHandler.class);

	// 提交完成任务
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		ReceiveReward receiveReward = (ReceiveReward) data;
		try {
			int param = receiveReward.getParam();
			int rewardType = receiveReward.getRewardType();
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			PlayerReward rewardInfo = taskService.playerRewardInfo(player.getId());
			int level;
			boolean isget = true;
			switch (rewardType) {
				case 2 :
					if (rewardInfo.getLoginDays() < param)
						return;
					isget = rewardInfo.isLoginReward(param);
					break;
				case 3 :
					if (rewardInfo.getLoginDays() < param)
						return;
					isget = rewardInfo.isLoginTargetReward(param);
					break;
				case 4 :
					level = player.getLevel();
					if (level < param)
						return;
					isget = rewardInfo.isLevelReward(param);
					break;
				case 5 :
					level = player.getLevel();
					if (level < param)
						return;
					isget = rewardInfo.isLevelTargetReward(param);
					break;
				case 6 :
					if (System.currentTimeMillis() >= rewardInfo.getOnlineTime() && rewardInfo.getOnlineTime() > 0)
						isget = false;
					param = rewardInfo.getOnlineMinutes();
					break;
				case 7 :
					if (rewardInfo.getLotteryTimes() > 0)
						isget = false;
					param = rewardInfo.getLotteryParam();
					break;
			}
			Reward signReward = taskService.getRewardByParam(param, rewardType);
			// 判断是否已领取
			if (!isget && (7 == rewardType || (null != signReward && signReward.getParam() == param))) {
				List<RewardInfo> riList = null;
				switch (rewardType) {
					case 2 :
						String loginRewards = rewardInfo.getLoginRewards();
						if (loginRewards.length() < 1) {
							loginRewards += param;
						} else {
							loginRewards += ("," + param);
						}
						rewardInfo.updateLoginRewards(loginRewards);
						taskService.savePlayerSignInfo(player.getId());
						riList = taskService.sendSignReward(signReward.getReward(), player, 36, TradeService.ORIGIN_LOGIN_REWARD, "登录奖励");
						break;
					case 3 :
						String loginTargetRewards = rewardInfo.getLoginTargetRewards();
						if (loginTargetRewards.length() < 1) {
							loginTargetRewards += param;
						} else {
							loginTargetRewards += ("," + param);
						}
						rewardInfo.updateLoginTargetRewards(loginTargetRewards);
						taskService.savePlayerSignInfo(player.getId());
						riList = taskService.sendSignReward(signReward.getReward(), player, 36, TradeService.ORIGIN_LOGIN_REWARD, "登录奖励");
						break;
					case 4 :
						String levelRewards = rewardInfo.getLevelRewards();
						if (levelRewards.length() < 1) {
							levelRewards += param;
						} else {
							levelRewards += ("," + param);
						}
						rewardInfo.updateLevelRewards(levelRewards);
						taskService.savePlayerSignInfo(player.getId());
						riList = taskService.sendSignReward(signReward.getReward(), player, 37, TradeService.ORIGIN_LEVEL_REWARD, "等级奖励");
						break;
					case 5 :
						String levelTargetRewards = rewardInfo.getLevelTargetRewards();
						if (levelTargetRewards.length() < 1) {
							levelTargetRewards += param;
						} else {
							levelTargetRewards += ("," + param);
						}
						rewardInfo.updateLevelTargetRewards(levelTargetRewards);
						taskService.savePlayerSignInfo(player.getId());
						riList = taskService.sendSignReward(signReward.getReward(), player, 37, TradeService.ORIGIN_LEVEL_REWARD, "等级奖励");
						break;
					case 6 :
						rewardInfo.receiveOnlineReward();
						taskService.savePlayerSignInfo(player.getId());
						riList = taskService.sendSignReward(signReward.getReward(), player, 27, TradeService.ORIGIN_ONLINE_REWARD, "在线奖励");
						break;
					case 7 :
						rewardInfo.receiveLotteryReward();
						taskService.savePlayerSignInfo(player.getId());
						// 随机获取数量
						List<Reward> rList = taskService.getLotteryRewardList();
						StringBuffer reward = new StringBuffer();
						for (Reward r : rList) {
							reward.append(r.getReward());
							reward.append("&");
						}
						if (reward.length() > 0)
							reward.deleteCharAt(reward.length() - 1);
						riList = taskService.sendSignReward(reward.toString(), player, 38, TradeService.ORIGIN_LOTTERY_REWARD, "在线抽奖奖励");
						break;
				}
				List<Integer> rewardItems = new ArrayList<Integer>();
				List<Integer> rewardCount = new ArrayList<Integer>();
				if (riList != null) {
					for (RewardInfo ri : riList) {
						rewardItems.add(ri.getItemId());
						rewardCount.add(ri.getCount());
					}
				}
				ReceiveRewardOk receiveRewardOk = new ReceiveRewardOk(data.getSessionId(), data.getSerial());
				receiveRewardOk.setRewardItems(ServiceUtils.getInts(rewardItems.toArray()));
				receiveRewardOk.setRewardCount(ServiceUtils.getInts(rewardCount.toArray()));
				session.write(receiveRewardOk);
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
