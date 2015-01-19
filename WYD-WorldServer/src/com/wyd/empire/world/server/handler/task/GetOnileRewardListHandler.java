package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.GetOnileRewardListOk;
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
 * 获取玩家在线奖励列表
 * 
 * @author zguoqiu
 */
public class GetOnileRewardListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetOnileRewardListHandler.class);

	// 获取玩家在线奖励列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			PlayerReward rewardInfo = taskService.playerRewardInfo(player.getId());
			rewardInfo.checkLotteryReward();
			int onlineTime = (int) ((rewardInfo.getOnlineTime() - System.currentTimeMillis()) / 1000);
			onlineTime = onlineTime > 0 ? onlineTime : 0;
			List<Integer> onlineItem = new ArrayList<Integer>();
			List<Integer> onlineCount = new ArrayList<Integer>();
			if (rewardInfo.getOnlineTime() > 0) {// 当需求在线时间大于0时才能有奖励
				Reward reward = taskService.getRewardByParam(rewardInfo.getOnlineMinutes(), 6);
				List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(reward.getReward(), player.getPlayer().getSex().intValue());
				for (RewardInfo ri : rewardList) {
					onlineItem.add(ri.getItemId());
					onlineCount.add(ri.getCount());
				}
			}
			int lotteryTime = (int) ((rewardInfo.getLotteryTime() - System.currentTimeMillis()) / 1000);
			lotteryTime = lotteryTime > 0 ? lotteryTime : 0;
			List<Integer> lotteryItem = new ArrayList<Integer>();
			int lotteryTimes = rewardInfo.getLotteryTimes();
			if (rewardInfo.getLotteryMinutes() > 0) {
				List<Reward> rList = taskService.getRewardList(7);
				for (Reward reward : rList) {
					List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(reward.getReward(), player.getPlayer().getSex().intValue());
					for (RewardInfo ri : rewardList) {
						lotteryItem.add(ri.getItemId());
					}
				}
			}
			// System.out.println("onlineTime:"+onlineTime+" lotteryTime:"+lotteryTime);
			GetOnileRewardListOk orlo = new GetOnileRewardListOk(data.getSessionId(), data.getSerial());
			orlo.setOnlineTime(onlineTime);
			orlo.setOnlineItem(ServiceUtils.getInts(onlineItem.toArray()));
			orlo.setOnlineCount(ServiceUtils.getInts(onlineCount.toArray()));
			orlo.setLotteryTime(lotteryTime);
			orlo.setLotteryItem(ServiceUtils.getInts(lotteryItem.toArray()));
			orlo.setLotteryTimes(lotteryTimes);
			session.write(orlo);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
