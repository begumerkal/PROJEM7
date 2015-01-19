package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.GetActiveReward;
import com.wyd.empire.protocol.data.task.GetActiveRewardOk;
import com.wyd.empire.protocol.data.task.GetActiveTaskList;
import com.wyd.empire.world.bean.ActiveReward;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.net.ProtocolFactory;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 领取活跃度奖励
 * 
 * @author zguoqiu
 */
public class GetActiveRewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetActiveRewardHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetActiveReward gar = (GetActiveReward) data;
		try {
			PlayerInfo playerInfo = player.getPlayerInfo();
			List<ActiveReward> activeRewardList = ServiceManager.getManager().getTaskService().getService().getActiveRewardList();
			int rewardIndex = gar.getRewardIndex() - 1;
			if (rewardIndex > -1 && rewardIndex < activeRewardList.size()) {
				ActiveReward activeReward = activeRewardList.get(rewardIndex);
				if (!playerInfo.isActiveReward(rewardIndex) && playerInfo.getActivity() >= activeReward.getActivityDemand()) {
					playerInfo.updateActiveRewards(rewardIndex);
					player.updatePlayerInfo();
					List<RewardInfo> riList = ServiceManager.getManager().getTaskService().getService()
							.sendSignReward(activeReward.getReward(), player, 26, TradeService.ACTIVE_REWARD, "活跃度奖励");
					GameLogService.activityReward(player.getId(), player.getLevel(), rewardIndex, activeReward.getReward());
					List<Integer> rewardItems = new ArrayList<Integer>();
					List<Integer> rewardCount = new ArrayList<Integer>();
					if (riList != null) {
						for (RewardInfo ri : riList) {
							rewardItems.add(ri.getItemId());
							rewardCount.add(ri.getCount());
						}
					}
					GetActiveRewardOk getActiveRewardOk = new GetActiveRewardOk(data.getSessionId(), data.getSerial());
					getActiveRewardOk.setRewardItems(ServiceUtils.getInts(rewardItems.toArray()));
					getActiveRewardOk.setRewardCount(ServiceUtils.getInts(rewardCount.toArray()));
					session.write(getActiveRewardOk);
					GetActiveTaskList getActiveTaskList = new GetActiveTaskList();
					getActiveTaskList.setHandlerSource(session);
					getActiveTaskList.setSessionId(data.getSessionId());
					ProtocolFactory.getDataHandler(GetActiveTaskList.class).handle(getActiveTaskList);
					if (!ServiceManager.getManager().getTaskService().getService().hasActiveReward(player)) {
						player.updateButtonInfo(Common.BUTTON_ID_ACTIVE, false, 0);
					}
				}
			}
		} catch (Exception ex) {
			this.log.error(ex, ex);
		}
	}
}
