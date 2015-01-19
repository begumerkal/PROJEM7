package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.GetLevelRewardListOk;
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
 * 获取玩家等级奖励列表
 * 
 * @author zguoqiu
 */
public class GetLevelRewardListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetLevelRewardListHandler.class);

	// 获取玩家显示的任务列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			PlayerReward rewardInfo = taskService.playerRewardInfo(player.getId());
			List<Reward> srList = taskService.getRewardList(4);
			srList.addAll(taskService.getRewardList(5));
			List<Integer> types = new ArrayList<Integer>();
			List<Integer> levels = new ArrayList<Integer>();
			List<Boolean> rewards = new ArrayList<Boolean>();
			List<Integer> rewardCount = new ArrayList<Integer>();
			List<Integer> itemIds = new ArrayList<Integer>();
			List<Integer> itemCount = new ArrayList<Integer>();
			for (Reward reward : srList) {
				types.add(reward.getType());
				levels.add(reward.getParam());
				if (reward.getType() == 4) {
					rewards.add(rewardInfo.isLevelReward(reward.getParam()));
				}
				if (reward.getType() == 5) {
					rewards.add(rewardInfo.isLevelTargetReward(reward.getParam()));
				}
				List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(reward.getReward(), player.getPlayer().getSex().intValue());
				rewardCount.add(rewardList.size());
				for (RewardInfo ri : rewardList) {
					itemIds.add(ri.getItemId());
					itemCount.add(ri.getCount());
				}
			}
			GetLevelRewardListOk glrloList = new GetLevelRewardListOk(data.getSessionId(), data.getSerial());
			glrloList.setPlayerLevel(player.getLevel());
			glrloList.setPlayerRebirth(player.getPlayer().getZsLevel() > 0);
			glrloList.setTypes(ServiceUtils.getInts(types.toArray()));
			glrloList.setLevels(ServiceUtils.getInts(levels.toArray()));
			glrloList.setRewards(ServiceUtils.getBooleans(rewards.toArray()));
			glrloList.setRewardCount(ServiceUtils.getInts(rewardCount.toArray()));
			glrloList.setItemIds(ServiceUtils.getInts(itemIds.toArray()));
			glrloList.setItemCount(ServiceUtils.getInts(itemCount.toArray()));
			session.write(glrloList);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
