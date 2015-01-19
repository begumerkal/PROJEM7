package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.SendEverydayRewardList;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ITaskService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家每日登陆奖励列表
 * 
 * @author zguoqiu
 */
public class GetEverydayRewardListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetEverydayRewardListHandler.class);

	// 获取玩家显示的任务列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			PlayerReward rewardInfo = taskService.playerRewardInfo(player.getId());
			List<Reward> srList = taskService.getRewardList(2);
			srList.addAll(taskService.getRewardList(3));
			List<Integer> types = new ArrayList<Integer>();
			List<Integer> days = new ArrayList<Integer>();
			List<Boolean> rewards = new ArrayList<Boolean>();
			List<Integer> rewardCount = new ArrayList<Integer>();
			List<Integer> itemIds = new ArrayList<Integer>();
			List<Integer> itemCount = new ArrayList<Integer>();
			for (Reward reward : srList) {
				types.add(reward.getType());
				days.add(reward.getParam());
				if (reward.getType() == 2) {
					rewards.add(rewardInfo.isLoginReward(reward.getParam()));
				}
				if (reward.getType() == 3) {
					rewards.add(rewardInfo.isLoginTargetReward(reward.getParam()));
				}
				List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(reward.getReward(), player.getPlayer().getSex().intValue());
				rewardCount.add(rewardList.size());
				for (RewardInfo ri : rewardList) {
					itemIds.add(ri.getItemId());
					itemCount.add(ri.getCount());
				}
			}
			SendEverydayRewardList sendEverydayRewardList = new SendEverydayRewardList(data.getSessionId(), data.getSerial());
			sendEverydayRewardList.setLoginDays(rewardInfo.getLoginDays());
			sendEverydayRewardList.setTypes(ServiceUtils.getInts(types.toArray()));
			sendEverydayRewardList.setDays(ServiceUtils.getInts(days.toArray()));
			sendEverydayRewardList.setReward(ServiceUtils.getBooleans(rewards.toArray()));
			sendEverydayRewardList.setRewardCount(ServiceUtils.getInts(rewardCount.toArray()));
			sendEverydayRewardList.setItemIds(ServiceUtils.getInts(itemIds.toArray()));
			sendEverydayRewardList.setItemCount(ServiceUtils.getInts(itemCount.toArray()));
			session.write(sendEverydayRewardList);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
