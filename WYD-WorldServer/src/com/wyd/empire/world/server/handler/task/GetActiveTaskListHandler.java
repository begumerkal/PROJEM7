package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.SendActiveTaskList;
import com.wyd.empire.world.bean.ActiveReward;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ITaskService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家显示的活跃度任务列表
 * 
 * @author zguoqiu
 */
public class GetActiveTaskListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetActiveTaskListHandler.class);

	// 获取玩家显示的任务列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			List<PlayerTask> playerTaskList = player.getTaskIngList();
			List<Integer> taskId = new ArrayList<Integer>();
			List<Byte> taskStatus = new ArrayList<Byte>();
			List<Integer> targetStatus = new ArrayList<Integer>();
			List<Integer> targetValue = new ArrayList<Integer>();
			List<Integer> hyz = new ArrayList<Integer>();
			int activity = player.getPlayerInfo().getActivity();
			List<ActiveReward> activeRewardList = taskService.getActiveRewardList();
			int taskCount = activeRewardList.size();
			int[] activityDemand = new int[taskCount];
			boolean[] isReward = new boolean[taskCount];
			int[] rewardCount = new int[taskCount];
			List<Integer> itemIds = new ArrayList<Integer>();
			List<Integer> itemCount = new ArrayList<Integer>();
			PlayerTask playerTask;
			for (int i = 0; i < playerTaskList.size(); i++) {
				playerTask = playerTaskList.get(i);
				if (playerTask.getTaskType() != Common.TASK_TYPE_ACTIVE)
					continue;
				List<Integer> tvList = new ArrayList<Integer>();
				List<Integer> tsList = new ArrayList<Integer>();
				for (int index = 0; index < playerTask.getTargetStatus().length; index++) {
					tvList.add(playerTask.getTargetValue()[index]);
					tsList.add(playerTask.getTargetStatus()[index]);
				}
				Task task = taskService.getTaskById(playerTask.getTaskId());
				List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(task.getReward(), player.getPlayer().getSex().intValue());
				int h = rewardList.size() > 0 ? rewardList.get(0).getCount() : 0;
				// 任务未完成则排序在前面
				if (playerTask.getStatus() < Common.TASK_STATUS_FINISHED) {
					taskId.add(0, playerTask.getTaskId());
					taskStatus.add(0, playerTask.getStatus());
					targetValue.addAll(0, tvList);
					targetStatus.addAll(0, tsList);
					hyz.add(0, h);
				} else {
					taskId.add(playerTask.getTaskId());
					taskStatus.add(playerTask.getStatus());
					targetValue.addAll(tvList);
					targetStatus.addAll(tsList);
					hyz.add(h);
				}
			}
			ActiveReward activeReward;
			for (int i = 0; i < taskCount; i++) {
				activeReward = activeRewardList.get(i);
				activityDemand[i] = activeReward.getActivityDemand();
				isReward[i] = player.getPlayerInfo().isActiveReward(i);
				List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(activeReward.getReward(), player.getPlayer().getSex().intValue());
				rewardCount[i] = rewardList.size();
				for (RewardInfo ri : rewardList) {
					itemIds.add(ri.getItemId());
					itemCount.add(ri.getCount());
				}
			}
			SendActiveTaskList sendTaskList = new SendActiveTaskList(data.getSessionId(), data.getSerial());
			sendTaskList.setTaskId(ServiceUtils.getInts(taskId.toArray()));
			sendTaskList.setTaskStatus(ServiceUtils.getBytes(taskStatus.toArray()));
			sendTaskList.setTargetStatus(ServiceUtils.getInts(targetStatus.toArray()));
			sendTaskList.setTargetValue(ServiceUtils.getInts(targetValue.toArray()));
			sendTaskList.setHyz(ServiceUtils.getInts(hyz.toArray()));
			sendTaskList.setActivity(activity);
			sendTaskList.setActivityDemand(activityDemand);
			sendTaskList.setIsReward(isReward);
			sendTaskList.setRewardCount(rewardCount);
			sendTaskList.setItemIds(ServiceUtils.getInts(itemIds.toArray()));
			sendTaskList.setItemCount(ServiceUtils.getInts(itemCount.toArray()));
			session.write(sendTaskList);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GTLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
