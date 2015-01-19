package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.SendTaskList;
import com.wyd.empire.world.bean.DayTask;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ITaskService;
import com.wyd.empire.world.server.service.base.impl.PlayerTaskTitleService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家显示的任务列表
 * 
 * @author Administrator
 */
public class GetTaskListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetTaskListHandler.class);

	// 获取玩家显示的任务列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			if (null == player)
				return;
			List<PlayerTask> playerTaskList = player.getTaskIngList();
			if (null == playerTaskList)
				return;
			List<Integer> taskId = new ArrayList<Integer>();
			List<Byte> taskType = new ArrayList<Byte>();
			List<Byte> taskSubType = new ArrayList<Byte>();
			List<Byte> taskStatus = new ArrayList<Byte>();
			List<Integer> targetCount = new ArrayList<Integer>();
			List<Byte> targetType = new ArrayList<Byte>();
			List<Integer> targetStatus = new ArrayList<Integer>();
			List<Integer> targetValue = new ArrayList<Integer>();
			List<String> parenthesis = new ArrayList<String>();
			List<Integer> diamond = new ArrayList<Integer>();
			List<Integer> upLevel = new ArrayList<Integer>();
			List<Integer> itemCount = new ArrayList<Integer>();
			List<Integer> itemId = new ArrayList<Integer>();
			List<Integer> expend = new ArrayList<Integer>();
			List<Integer> upCount = new ArrayList<Integer>();
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			for (PlayerTask playerTask : playerTaskList) {
				if (playerTask.getTaskType() != Common.TASK_TYPE_ACTIVE) {
					// 如果主线任务并且状态是1或者2，如果是日常任务状态大于0
					if ((playerTask.getTaskType() == Common.TASK_TYPE_MAIN && (playerTask.getStatus() == Common.TASK_STATUS_FINISHING || playerTask
							.getStatus() == Common.TASK_STATUS_SUBMIT))
							|| (playerTask.getTaskType() == Common.TASK_TYPE_DAY && playerTask.getStatus() > Common.TASK_STATUS_UNTRIGGERED)) {
						// 日常任务61级后才触发
						if (playerTask.getTaskType() == Common.TASK_TYPE_DAY && player.getLevel() < 61)
							continue;
						taskId.add(playerTask.getTaskId());
						taskType.add(playerTask.getTaskType());
						taskStatus.add(playerTask.getStatus());
						targetCount.add(playerTask.getTargetStatus().length);
						for (int value : playerTask.getTargetStatus()) {
							targetStatus.add(value);
						}
						for (int value : playerTask.getTargetValue()) {
							targetValue.add(value);
						}
						taskSubType.add(playerTask.getTaskSubType());
						// System.out.println("taskId:" + playerTask.getTaskId()
						// + " TaskType:" + playerTask.getTaskType() +
						// " SubType:" + playerTask.getTaskSubType());
						targetType.add(playerTask.getTargetType());
						parenthesis.add(playerTask.getParenthesis());
						upLevel.add(playerTask.getUpLevel() + 1);
						List<RewardInfo> rewardList;
						if (playerTask.getTaskType() == Common.TASK_TYPE_MAIN) {
							Task task = taskService.getTaskById(playerTask.getTaskId());
							rewardList = task.getRewardBySex(player.getPlayer().getSex().intValue());
							itemCount.add(rewardList.size());
							diamond.add(0);
							for (int i = 0; i <= Common.TASK_TOP_LEVEL; i++) {
								expend.add(0);
							}
							for (RewardInfo info : rewardList) {
								for (int i = 0; i <= Common.TASK_TOP_LEVEL; i++) {
									upCount.add(info.getCount());
								}
							}
						} else {
							DayTask task = taskService.getDayTaskById(playerTask.getTaskId());
							rewardList = task.getRewardBySex(player.getPlayer().getSex().intValue());
							itemCount.add(rewardList.size());
							diamond.add(task.getQuickCost());
							expend.addAll(task.getExpendList());
							upCount.addAll(task.getRewardAddition(player.getPlayer().getSex().intValue(), player.getPlayer().getVipLevel(),
									player.getLevel()));
						}
						for (RewardInfo reward : rewardList) {
							itemId.add(reward.getItemId());
						}
					}
				}
			}
			SendTaskList sendTaskList = new SendTaskList(data.getSessionId(), data.getSerial());
			sendTaskList.setTaskStar(PlayerTaskTitleService.getTaskStar(player.getPlayer().getVipLevel()));
			sendTaskList.setTaskTopLevel(Common.TASK_TOP_LEVEL);
			sendTaskList.setTaskId(ServiceUtils.getInts(taskId.toArray()));
			sendTaskList.setTaskType(ServiceUtils.getBytes(taskType.toArray()));
			sendTaskList.setTaskSubType(ServiceUtils.getBytes(taskSubType.toArray()));
			sendTaskList.setTaskStatus(ServiceUtils.getBytes(taskStatus.toArray()));
			sendTaskList.setTargetCount(ServiceUtils.getInts(targetCount.toArray()));
			sendTaskList.setTargetType(ServiceUtils.getBytes(targetType.toArray()));
			sendTaskList.setTargetStatus(ServiceUtils.getInts(targetStatus.toArray()));
			sendTaskList.setTargetValue(ServiceUtils.getInts(targetValue.toArray()));
			sendTaskList.setParenthesis(parenthesis.toArray(new String[]{}));
			sendTaskList.setDiamond(ServiceUtils.getInts(diamond.toArray()));
			sendTaskList.setUpLevel(ServiceUtils.getInts(upLevel.toArray()));
			sendTaskList.setItemCount(ServiceUtils.getInts(itemCount.toArray()));
			sendTaskList.setItemId(ServiceUtils.getInts(itemId.toArray()));
			sendTaskList.setExpend(ServiceUtils.getInts(expend.toArray()));
			sendTaskList.setUpCount(ServiceUtils.getInts(upCount.toArray()));
			session.write(sendTaskList);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GTLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
