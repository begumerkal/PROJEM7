package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.bulletin.WeiboShare;
import com.wyd.empire.protocol.data.task.GetCommitTaskNum;
import com.wyd.empire.protocol.data.task.StatusChanged;
import com.wyd.empire.world.bean.ButtonInfo;
import com.wyd.empire.world.bean.DayTask;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.bean.PlayerTaskTitle;
import com.wyd.empire.world.bean.Share;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.bean.Title;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.SerializeUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.dao.IPlayerTaskTitleDao;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerTaskTitleService;
import com.wyd.empire.world.server.service.base.IShareService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.SystemLogService;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.empire.world.task.TaskKey;
import com.wyd.empire.world.title.PlayerTitleVo;
import com.wyd.empire.world.title.TitleIng;

/**
 * The service class for the TabPlayerTaskTitle entity.
 */
public class PlayerTaskTitleService extends UniversalManagerImpl implements IPlayerTaskTitleService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerTaskTitleDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PlayerTaskTitleService";

	public PlayerTaskTitleService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IPlayerTaskTitleService getInstance(ApplicationContext context) {
		return (IPlayerTaskTitleService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPlayerTaskTitleDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerTaskTitleDao getDao() {
		return this.dao;
	}

	/**
	 * 登录和每天凌晨更新玩家日常活动任务
	 * 
	 * @param player
	 *            玩家信息
	 * @param isCheck
	 *            是否需要检测缓存中的任务
	 */
	public void initialPlayerTask(WorldPlayer player) {
		if (null == player.getPlayer().getTaskUpdateTime()
				|| 0 != DateUtil.compareDateOnDay(player.getPlayer().getTaskUpdateTime(), new Date())) {
			player.getPlayer().setTaskUpdateTime(new Date());// 重置任务时间
			initialDayTask(player.getTaskIngList());
			activateDayTask(player, player.getTaskIngList());
			player.resetActiveReward();// 重置活跃度任务的奖励信息
			player.resetButtonInfo();// 重置玩家按钮信息
		}
	}

	/**
	 * 激活每日任务
	 */
	public int activateDayTask(WorldPlayer player, List<PlayerTask> playerTaskList) {
		int initCount = 0;
		if (playerTaskList != null && !playerTaskList.isEmpty()) {
			for (PlayerTask playerTask : playerTaskList) {
				if (playerTask.getTaskType() == Common.TASK_TYPE_ACTIVE) {
					updatePlayerTaskStatus(player, playerTask, Common.TASK_STATUS_FINISHING, false);
					for (int i = 0; i < playerTask.getTargetStatus().length; i++) {
						updatePlayerTaskTarget(player, playerTask, i, 0, false);
					}
					initCount++;
				}
			}
			int activateNum = 0;
			List<DayTask> dayTaskList = ServiceManager.getManager().getTaskService().getService().getAllDayTaskList();
			DayTask dayTask = null;
			while (activateNum < Common.TASK_DAY_NUM && dayTaskList.size() > 0) {
				if (null != dayTask) {
					dayTask = dayTaskList.remove(ServiceUtils.getRandomNum(0, dayTaskList.size()));
				} else {
					dayTask = ServiceManager.getManager().getTaskService().getService().getDayTaskById(TaskKey.DAY_TASK_ID_DAY);
					dayTaskList.remove(dayTask);
				}
				if (activateNum < Common.TASK_DAY_NUM) {
					List<Integer> typeList = new ArrayList<Integer>(dayTask.getTypeList());
					List<Integer> typeWeightsList = new ArrayList<Integer>(dayTask.getTypeWeightsList());
					for (int i = typeList.size() - 1; i > -1; i--) {
						int aId = dayTask.getActivateIdList().get(i);
						PlayerTask aTask = null;
						if (aId > 0) {
							aTask = player.getTaskIngByTaskId(aId, Common.TASK_TYPE_MAIN);
						}
						int dId = dayTask.getDisableIdList().get(i);
						PlayerTask dTask = null;
						if (dId > 0) {
							dTask = player.getTaskIngByTaskId(dId, Common.TASK_TYPE_MAIN);
						}
						if ((null != aTask && aTask.getStatus() < Common.TASK_STATUS_FINISHED)
								|| (null != dTask && dTask.getStatus() > Common.TASK_STATUS_SUBMIT)) {
							typeList.remove(i);
							typeWeightsList.remove(i);
						}
					}
					int typeWeights = 0;
					for (Integer weights : typeWeightsList) {
						typeWeights += weights;
					}
					if (typeWeights > 0) {
						byte type = 0;
						int target = 0;
						int random = ServiceUtils.getRandomNum(0, typeWeights);
						typeWeights = 0;
						for (int i = 0; i < typeWeightsList.size(); i++) {
							typeWeights += typeWeightsList.get(i);
							if (random < typeWeights) {
								type = typeList.get(i).byteValue();
								continue;
							}
						}
						typeWeights = 0;
						for (Integer weights : dayTask.getTargetWeightsList()) {
							typeWeights += weights;
						}
						random = ServiceUtils.getRandomNum(0, typeWeights);
						typeWeights = 0;
						for (int i = 0; i < dayTask.getTargetWeightsList().size(); i++) {
							typeWeights += dayTask.getTargetWeightsList().get(i);
							if (random < typeWeights) {
								target = dayTask.getTargetList().get(i);
								continue;
							}
						}
						PlayerTask playerTask = new PlayerTask(dayTask.getId(), type, (byte) target,
								new int[]{dayTask.getTargetValue(player.getLevel())});
						updatePlayerTaskStatus(player, playerTask, Common.TASK_STATUS_FINISHING, false);
						playerTaskList.add(playerTask);
						activateNum++;
						switch (playerTask.getTaskId()) {
							case TaskKey.DAY_TASK_ID_SINFLE :
								if (1 != playerTask.getTaskSubType()) {
									Map maxPassMap = ServiceManager.getManager().getSingleMapService().getMaxPassMap(player.getId());
									Integer maxId;
									if (maxPassMap == null) {
										maxId = Common.STARTSINGLEMAPID;
									} else {
										maxId = maxPassMap.getId();
									}
									if (2 == playerTask.getTaskSubType()) {
										playerTask.setParenthesis(maxId.toString());
									} else if (3 == playerTask.getTaskSubType()) {
										List<Map> mapList = ServiceManager.getManager().getMapsService().getSingleMapList();
										List<Integer> newList = new ArrayList<Integer>();
										for (Map map : mapList) {
											if (map.getId().intValue() <= maxId) {
												newList.add(map.getId());
											}
										}
										playerTask.setParenthesis(newList.get(ServiceUtils.getRandomNum(0, newList.size())).toString());
									}
								}
								break;
							case TaskKey.DAY_TASK_ID_COPY :
								if (1 != playerTask.getTaskSubType()) {
									List<Map> mapList = ServiceManager.getManager().getMapsService().getBossMapList();
									Integer maxId = 0;
									for (Map map : mapList) {
										if (map.getBossmapSerial() == player.getBossmap_progress()) {
											maxId = map.getId();
											break;
										}
									}
									if (2 == playerTask.getTaskSubType()) {
										playerTask.setParenthesis(maxId.toString());
									} else if (3 == playerTask.getTaskSubType()) {
										List<Integer> newList = new ArrayList<Integer>();
										for (Map map : mapList) {
											if (map.getId().intValue() <= maxId) {
												newList.add(map.getId());
											}
										}
										playerTask.setParenthesis(newList.get(ServiceUtils.getRandomNum(0, newList.size())).toString());
									}
								}
								break;
						}
						initCount++;
					}
				}
			}
		}
		return initCount;
	}

	/**
	 * 重置日常
	 * 
	 * @param taskIngList
	 *            任务列表
	 */
	public void initialDayTask(List<PlayerTask> taskIngList) {
		System.out.println("initialDayTask--------------------------");
		if (taskIngList != null && !taskIngList.isEmpty()) {
			PlayerTask taskIng;
			for (int i = taskIngList.size() - 1; i >= 0; i--) {
				taskIng = taskIngList.get(i);
				if (taskIng.getTaskType() == Common.TASK_TYPE_DAY) {
					taskIngList.remove(i);
				}
			}
		}
	}

	/**
	 * 初始化主线任务
	 * 
	 * @param playerTaskList
	 *            玩家任务列表
	 * @param level
	 *            玩家等级
	 * @return 触发任务数量
	 */
	public int initialTask(WorldPlayer player, List<PlayerTask> playerTaskList, int level, int playerId) {
		int initCount = 0;
		if (playerTaskList != null && !playerTaskList.isEmpty()) {
			for (Object obj : playerTaskList) {
				PlayerTask playerTask = (PlayerTask) obj;
				if (playerTask.getTaskType() == Common.TASK_TYPE_MAIN && playerTask.getStatus() == Common.TASK_STATUS_UNTRIGGERED
						&& this.isTriggerTask(playerTask.getTaskId(), playerTaskList, level)) {
					Task task = ServiceManager.getManager().getTaskService().getService().getTaskById(playerTask.getTaskId());
					if (null != task.getTarget() && task.getTarget().indexOf(TaskKey.QHWP) > -1 && task.getTarget().indexOf("&") < 0) {
						// qhwp*-1*1=1
						String l = task.getTarget().split("\\*")[2].split("=")[0];
						if (Integer.parseInt(l) <= ServiceManager.getManager().getPlayerItemsFromShopService()
								.getTopLevelForItemByPlayer(playerId)) {
							updatePlayerTaskStatus(player, playerTask, Common.TASK_STATUS_SUBMIT, false);
							updatePlayerTaskTarget(player, playerTask, -1, 0, true);
						} else {
							updatePlayerTaskStatus(player, playerTask, Common.TASK_STATUS_FINISHING, true);
						}
					} else if (null != task.getTarget() && task.getTarget().indexOf(TaskKey.SXWP) > -1 && task.getTarget().indexOf("&") < 0) {
						// sxwp*-1*1=1
						String s = task.getTarget().split("\\*")[2].split("=")[0];
						if (Integer.parseInt(s) <= ServiceManager.getManager().getPlayerItemsFromShopService()
								.getTopStarForPlayer(playerId)) {
							updatePlayerTaskStatus(player, playerTask, Common.TASK_STATUS_SUBMIT, false);
							updatePlayerTaskTarget(player, playerTask, -1, 0, true);
						} else {
							updatePlayerTaskStatus(player, playerTask, Common.TASK_STATUS_FINISHING, true);
						}
					} else {
						updatePlayerTaskStatus(player, playerTask, Common.TASK_STATUS_FINISHING, true);
					}
					initCount++;
				}
			}
		}
		return initCount;
	}

	/**
	 * 判断任务是否触发
	 * 
	 * @param taskId
	 *            任务ID
	 * @param playerTaskList
	 *            玩家任务列表
	 * @param level
	 *            玩家等级
	 * @return 主线或支线任务是否触发
	 */
	public boolean isTriggerTask(int taskId, List<PlayerTask> playerTaskList, int level) {
		boolean bResult = false;
		try {
			Task task = ServiceManager.getManager().getTaskService().getService().getTaskById(taskId);
			if (task != null && task.getLevel() <= level && this.isAllParentTaskFinish(task, playerTaskList)) {
				bResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bResult;
	}

	/**
	 * 获取父ID列表
	 * 
	 * @param parentTaskId
	 *            父ID字符串
	 * @return 取父ID列表
	 */
	public List<Integer> getParentTaskIdList(String parentTaskId) {
		List<Integer> parentTaskIdList = null;
		if (parentTaskId != null && !parentTaskId.equals("")) {
			parentTaskIdList = new ArrayList<Integer>();
			for (String strId : parentTaskId.split(",")) {
				try {
					parentTaskIdList.add(Integer.parseInt(strId));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return parentTaskIdList;
	}

	/**
	 * 判断一个任务的全部父任务是否完成
	 * 
	 * @param task
	 *            目标任务
	 * @param playerTaskList
	 *            玩家所有任务列表
	 * @return 是否所有父任务都已经完成
	 */
	public boolean isAllParentTaskFinish(Task task, List<PlayerTask> playerTaskList) {
		if (null == task.getParentId() || task.getParentId().isEmpty()) {
			return true;
		}
		List<Integer> parentTaskIdList = getParentTaskIdList(task.getParentId());
		for (Object obj : playerTaskList) {
			int checkTaskId;
			byte status;
			PlayerTask playerTask = (PlayerTask) obj;
			checkTaskId = playerTask.getTaskId();
			status = playerTask.getStatus();
			if (parentTaskIdList.contains(checkTaskId) && status != Common.TASK_STATUS_FINISHED) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 创建默认任务列表
	 * 
	 * @param playerId
	 *            玩家信息
	 */
	public PlayerTaskTitle createDefaultTaskAndTitleList(WorldPlayer player) {
		List<PlayerTask> playerTaskList = createPlayerTask();
		initialTask(player, playerTaskList, 1, player.getId());
		activateDayTask(player, playerTaskList);
		// 保存任务与成就信息
		PlayerTaskTitle playerTaskTitle = new PlayerTaskTitle();
		playerTaskTitle.setPlayer(player.getPlayer());
		playerTaskTitle.setTaskList(SerializeUtil.serialize(playerTaskList));
		playerTaskTitle.setTitleList(SerializeUtil.serialize(createPlayerTitle()));
		player.getPlayer().setSynchonousTime(new Date());
		return (PlayerTaskTitle) this.save(playerTaskTitle);
	}

	/**
	 * 创建玩家的任务列表
	 * 
	 * @return
	 */
	public List<PlayerTask> createPlayerTask() {
		// 初始化默认任务列表
		List<PlayerTask> playerTaskList = new ArrayList<PlayerTask>();
		// 初始主线和活跃度任务
		List<Task> taskInfoList = ServiceManager.getManager().getTaskService().getTaskService().getAllTaskList();
		if (taskInfoList != null && !taskInfoList.isEmpty()) {
			for (Task obj : taskInfoList) {
				playerTaskList.add(new PlayerTask(obj.getId(), obj.getTaskType(), obj.getTargetValueList()));
			}
		}
		return playerTaskList;
	}

	/**
	 * 创建玩家的成就列表
	 * 
	 * @return
	 */
	public List<PlayerTitleVo> createPlayerTitle() {
		// 初始化默认成就列表
		List<PlayerTitleVo> playerTitleList = new ArrayList<PlayerTitleVo>();
		List<Integer> titleIdList = dao.getEffectiveTitleIdList();
		if (titleIdList != null && !titleIdList.isEmpty()) {
			for (Integer titleId : titleIdList) {
				playerTitleList.add(new PlayerTitleVo(titleId));
			}
		}
		return playerTitleList;
	}

	/**
	 * 获取正在进行的单个称号信息
	 * 
	 * @param playerTitleVo
	 *            称号完成情况信息
	 * @return
	 */
	public TitleIng getTitleIng(Title title, PlayerTitleVo playerTitleVo) {
		TitleIng titleIng = new TitleIng(title.getId());
		if (null == title.getTarget() || title.getTarget().equals("")) {
			titleIng.setStatus(Common.TITLE_STATUS_OBTAIN);
		} else {
			titleIng.setStatus(playerTitleVo.getStatus());
		}
		titleIng.setTarget(title.getTarget());
		titleIng.setTargetStatus(playerTitleVo.getTargetStatus());
		titleIng.init();
		return titleIng;
	}

	/**
	 * 根据玩家ID获取玩家任务，称号信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 取玩家任务，称号信息
	 */
	public PlayerTaskTitle getPlayerTaskTitleByPlayerId(int playerId) {
		return dao.getPlayerTaskTitleByPlayerId(playerId);
	}

	/**
	 * GM工具查询出任务列表
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return 任务列表
	 */
	public PageList findAllTask(String key, int pageIndex, int pageSize) {
		return dao.findAllTask(key, pageIndex, pageSize);
	}

	/**
	 * GM工具查询出任务列表
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return 列表
	 */
	public PageList findAllTitle(String key, int pageIndex, int pageSize) {
		return dao.findAllTitle(key, pageIndex, pageSize);
	}

	/**
	 * 根据等级获取当前会员排名
	 * 
	 * @param exp
	 *            经验
	 * @return 当前会员排名
	 */
	public int findCurrentRank(int exp) {
		long count = dao.findCurrentRank(exp);
		if (count == 0) {
			return 1;
		} else {
			return (int) count + 1;
		}
	}

	/**
	 * 根据玩家角色ID，获取玩家连接登录次数
	 * 
	 * @param playerId
	 *            玩家角色ID
	 * @param maxCount
	 *            最大返回条数
	 * @return 玩家连续登录次数
	 */
	public int getLoingNumByPlayerId(int playerId, int maxCount) {
		return dao.getLoingNumByPlayerId(playerId, maxCount);
	}

	/**
	 * 更新任称号务完成条件状态
	 * 
	 * @param titleIng
	 *            正在执行的称号任务信息
	 */
	public void updateTitleTargerStatus(TitleIng titleIng) {
		StringBuffer targetStatusBf = new StringBuffer();
		int i = 0;
		for (String[] trs : titleIng.getTitleList()) {
			StringBuffer targerBf = new StringBuffer();
			for (String tr : trs) {
				targerBf.append(tr).append(",");
			}
			String targer = targerBf.substring(0, targerBf.length() - 1);
			targetStatusBf.append(targer).append("=").append(titleIng.getFinishValuetList().get(i)).append("&");
			i++;
		}
		String targetStatus;
		if (targetStatusBf.length() > 0) {
			targetStatus = targetStatusBf.substring(0, targetStatusBf.length() - 1);
		} else {
			targetStatus = "";
		}
		titleIng.setTargetStatus(targetStatus);
	}

	/**
	 * 保存玩家未更新到数据库的任务，成就缓存信息
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 * @param Exception
	 *            保存数据出错时抛出此异常
	 */
	public void savePlayerTaskTitle(WorldPlayer worldPlayer) {
		try {
			PlayerTaskTitle playerTaskTitle = dao.getPlayerTaskTitleByPlayerId(worldPlayer.getId());
			if (playerTaskTitle != null && worldPlayer.getTaskIngList() != null && worldPlayer.getTitleIngList() != null) {
				playerTaskTitle.setTaskList(SerializeUtil.serialize(new ArrayList<PlayerTask>(worldPlayer.getTaskIngList())));
				playerTaskTitle.setTitleList(SerializeUtil.serialize(new ArrayList<PlayerTitleVo>(worldPlayer.getTitleIngList())));
				this.save(playerTaskTitle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存成就更新列表
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 * @return 目前玩家所有成就列表
	 */
	public List<PlayerTitleVo> savePlayerTitle(WorldPlayer worldPlayer) {
		PlayerTaskTitle playerTaskTitle = dao.getPlayerTaskTitleByPlayerId(worldPlayer.getId());
		if (playerTaskTitle == null) {
			playerTaskTitle = new PlayerTaskTitle();
			playerTaskTitle.setPlayer(worldPlayer.getPlayer());
		}
		List<PlayerTitleVo> playerTitleVoList = new ArrayList<PlayerTitleVo>();
		for (PlayerTitleVo playerTitleVo : worldPlayer.getTitleIngList()) {
			playerTitleVoList.add(playerTitleVo);
		}
		playerTaskTitle.setTitleList(SerializeUtil.serialize(playerTitleVoList));
		this.save(playerTaskTitle);
		return playerTitleVoList;
	}

	/**
	 * 保存玩家任务列表信息
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 * @throws Exception
	 *             复制对象内容出错时抛出此异常
	 */
	public List<PlayerTask> savePlayerTask(WorldPlayer worldPlayer) throws Exception {
		PlayerTaskTitle playerTaskTitle = dao.getPlayerTaskTitleByPlayerId(worldPlayer.getId());
		if (playerTaskTitle == null) {
			playerTaskTitle = new PlayerTaskTitle();
			playerTaskTitle.setPlayer(worldPlayer.getPlayer());
		}
		List<PlayerTask> playerTaskList = new ArrayList<PlayerTask>();
		for (PlayerTask playerTask : worldPlayer.getTaskIngList()) {
			playerTaskList.add(playerTask);
		}
		playerTaskTitle.setTaskList(SerializeUtil.serialize(playerTaskList));
		this.save(playerTaskTitle);
		return playerTaskList;
	}

	/**
	 * 获取最后任务更新日期
	 * 
	 * @return 最后任务更新日期
	 */
	public Date getLastTaskUpdateTime() {
		return dao.getLastTaskUpdateTime();
	}

	/**
	 * 获取称号最后更新日期
	 * 
	 * @return 称号最后更新日期
	 */
	public Date getLastTitleUpdateTime() {
		return dao.getLastTitleUpdateTime();
	}

	/**
	 * 获取可用任务列表
	 * 
	 * @return 可用任务列表
	 */
	public List<Task> getEffectiveTaskList() {
		return dao.getEffectiveTaskList();
	}

	/**
	 * 更新主线活跃度任务完成情况
	 * 
	 * @param playerTask
	 *            玩家任务对应信息
	 * @param findshScript
	 *            任务脚本
	 * @param count
	 *            数量
	 */
	public void playerTaskCheck(WorldPlayer worldPlayer, String findshScript, int count) {
		int wrongTaskId = 0;
		try {
			List<PlayerTask> taskIntList = worldPlayer.getTaskIngList();
			if (null == taskIntList) {
				return;
			}
			for (int index = taskIntList.size() - 1; index > -1; index--) {
				PlayerTask taskIng = taskIntList.get(index);
				if (taskIng.getTaskType() != Common.TASK_TYPE_DAY && taskIng.getStatus() == Common.TASK_STATUS_FINISHING) {
					wrongTaskId = taskIng.getTaskId();
					boolean isFinishTask = this.updatePlayerTask(worldPlayer, taskIng, findshScript, count);
					if (isFinishTask) {
						if (taskIng.getTaskType() == Common.TASK_TYPE_ACTIVE) {
							updateTaskFinishStatus(taskIng, worldPlayer);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Check Taks Fail: PlayerId[" + worldPlayer.getId() + "] TaskId[" + wrongTaskId + "]");
			log.error(e, e);
		}
	}

	/**
	 * 检查日常任务完成情况
	 * 
	 * @param worldPlayer
	 * @param dayTaskType
	 *            (以任务id区分)
	 * @param subTypes
	 * @param targets
	 * @param count
	 * @param parenthesis
	 */
	public void playerTaskCheck(WorldPlayer worldPlayer, int dayTaskType, List<Integer> subTypes, List<Integer> targets,
			List<Integer> count, String parenthesis) {
		List<PlayerTask> taskIntList = worldPlayer.getTaskIngList();
		for (PlayerTask playerTask : taskIntList) {
			if (playerTask.getTaskType() == Common.TASK_TYPE_DAY && playerTask.getStatus() < Common.TASK_STATUS_SUBMIT
					&& playerTask.getTaskId() == dayTaskType) {
				for (Integer subType : subTypes) {
					if (playerTask.getTaskSubType() == subType.intValue()) {
						if (playerTask.getTaskSubType() > 1
								&& (playerTask.getTaskId() == TaskKey.DAY_TASK_ID_SINFLE || playerTask.getTaskId() == TaskKey.DAY_TASK_ID_COPY)) {
							if (parenthesis.equals(playerTask.getParenthesis())) {
								int value = playerTask.getTargetStatus()[0] + 1;
								updatePlayerTaskTarget(worldPlayer, playerTask, 0, value, value < playerTask.getTargetValue()[0]);
								updateTaskStauts(worldPlayer, playerTask);
							}
						} else {
							for (int i = 0; i < targets.size(); i++) {
								if (playerTask.getTargetType() == targets.get(i).byteValue()) {
									int value = playerTask.getTargetStatus()[0] + count.get(i);
									updatePlayerTaskTarget(worldPlayer, playerTask, 0, value, value < playerTask.getTargetValue()[0]);
									updateTaskStauts(worldPlayer, playerTask);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 更新任务状态
	 * 
	 * @param worldPlayer
	 * @param playerTask
	 */
	public void updateTaskStauts(WorldPlayer worldPlayer, PlayerTask playerTask) {
		if (playerTask.getTargetStatus()[0] >= playerTask.getTargetValue()[0]) {
			updatePlayerTaskTarget(worldPlayer, playerTask, -1, 0, false);
			updatePlayerTaskStatus(worldPlayer, playerTask, Common.TASK_STATUS_SUBMIT, true);
		}
	}

	/**
	 * 更新任务完成情况
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 * @param taskIng
	 *            正在进行的任务信息
	 * @param findshScript
	 *            完成脚本
	 * @param count
	 *            完成数量
	 * @return 是否完成任务
	 */
	public boolean updatePlayerTask(WorldPlayer worldPlayer, PlayerTask taskIng, String findshScript, int count) {
		boolean isFinishTask = false;
		findshScript = "," + findshScript;
		Task task = ServiceManager.getManager().getTaskService().getService().getTaskById(taskIng.getTaskId());
		int i = 0;
		for (String[] splits : task.getTargetTitleList()) {
			boolean check = true;
			for (String split : splits) {
				String[] tjs = split.split("\\|");
				boolean checkt = false;
				for (String tj : tjs) {
					tj = "," + tj + ",";
					if (findshScript.indexOf(tj) != -1) {
						checkt = true;
						break;
					}
				}
				if (!checkt) {
					check = false;
					break;
				}
			}
			if (check && taskIng.getTargetStatus()[i] < taskIng.getTargetValue()[i]) {
				int value = taskIng.getTargetStatus()[i] + count;
				if (value > taskIng.getTargetValue()[i]) {
					value = taskIng.getTargetValue()[i];
				}
				updatePlayerTaskTarget(worldPlayer, taskIng, i, value, true);
			}
			i++;
		}
		boolean check = true;
		for (int y = 0; y < task.getTargetValueList().length; y++) {
			if (taskIng.getTargetValue()[y] > taskIng.getTargetStatus()[y]) {
				check = false;
				break;
			}
		}
		if (check) {
			updatePlayerTaskStatus(worldPlayer, taskIng, Common.TASK_STATUS_SUBMIT, true);
			isFinishTask = true;
		}
		return isFinishTask;
	}

	/**
	 * 将未完成的一次性任务重新初始化
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 */
	public void initialOneTimesTask(WorldPlayer worldPlayer) {
		List<PlayerTask> taskIngList = worldPlayer.getTaskIngList();
		if (taskIngList != null && !taskIngList.isEmpty()) {
			for (PlayerTask playerTask : taskIngList) {
				if (playerTask.getStatus() == Common.TASK_STATUS_FINISHING && playerTask.getTaskType() != Common.TASK_TYPE_DAY) {
					Task task = ServiceManager.getManager().getTaskService().getService().getTaskById(playerTask.getTaskId());
					if (task.getTarget().startsWith(Common.TASK_TYPE_SINGLE)) {
						for (int y = 0; y < playerTask.getTargetStatus().length; y++) {
							updatePlayerTaskTarget(worldPlayer, playerTask, y, 0, true);
						}
					}
				}
			}
		}
	}

	/**
	 * 将未完成的一次性成就重新初始化
	 * 
	 * @param worldplayer
	 *            玩家信息
	 */
	public void initialOneTimesTitle(WorldPlayer worldplayer) {
		List<TitleIng> titleIngList = worldplayer.getTitleIngList();
		if (titleIngList != null && !titleIngList.isEmpty()) {
			for (TitleIng titleIng : titleIngList) {
				if (titleIng.getStatus() == Common.TITLE_STATUS_NOT_OBTAIN && titleIng.getTarget().startsWith(Common.TASK_TYPE_SINGLE)) {
					for (int y = 0; y < titleIng.getFinishValuetList().size(); y++) {
						titleIng.getFinishValuetList().set(y, 0);
					}
				}
			}
		}
	}

	/**
	 * 更新称号我任务完成情况
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 * @param findshScript
	 *            执行脚本
	 * @param count
	 *            完成次数
	 */
	public void titleTaskCheck(WorldPlayer worldPlayer, String findshScript, int count) {
		try {
			List<TitleIng> titleIntList = worldPlayer.getTitleIngList();
			if (null == titleIntList) {
				return;
			}
			for (int index = 0; index < titleIntList.size(); index++) {
				TitleIng titleIng = titleIntList.get(index);
				if (titleIng.getStatus() == Common.TITLE_STATUS_NOT_OBTAIN) {
					boolean isFinishTitle = updatePlayerTitle(titleIng, findshScript, count);
					if (isFinishTitle) {
						if (weiboIsOpen(worldPlayer)) {
							// 触发微博分享
							weiboShare(worldPlayer, titleIng);
						}
						GameLogService.title(worldPlayer.getId(), worldPlayer.getLevel(), titleIng.getTitleId());
					}
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	/**
	 * 微博功能是否开启
	 * 
	 * @param worldPlayer
	 * @return
	 */
	private boolean weiboIsOpen(WorldPlayer worldPlayer) {
		ButtonInfo button = ServiceManager.getManager().getOperationConfigService().getButtonInfoById(35);
		int openlevel = button.getButtonStatus3Level();
		return worldPlayer.getLevel() >= openlevel;
	}

	/**
	 * 向客户端推送微博分享内容
	 * 
	 * @param worldPlayer
	 * @param titleIng
	 */
	private void weiboShare(WorldPlayer worldPlayer, TitleIng titleIng) {
		OperationConfig operationConfig = ServiceManager.getManager().getVersionService().getVersion();
		WeiboShare weiboShare = new WeiboShare();
		IShareService shareService = ServiceManager.getManager().getShareService();
		String target = "chid=" + titleIng.getTitleId();
		Share share = shareService.getShareByTarget(target);
		if (share != null) {
			weiboShare.setContent(share.getContent());
			weiboShare.setPicurl(operationConfig.getWbPicUrl());
			weiboShare.setWbAppKey(operationConfig.getWbAppKey());
			weiboShare.setWebAppSecret(operationConfig.getWebAppSecret());
			weiboShare.setWebAppRedirectUri(operationConfig.getWebAppRedirectUri());
			weiboShare.setWbUid(operationConfig.getWbUid());
			weiboShare.setEdit(share.getShareType() == 1);
			worldPlayer.sendData(weiboShare);
		}
	}

	/**
	 * 更新玩家称号
	 * 
	 * @param titleIng
	 *            还未完成的称号信息
	 * @param findshScript
	 *            完成脚本
	 * @param count
	 *            完成次数
	 * @return 是否有完成的称号
	 */
	public boolean updatePlayerTitle(TitleIng titleIng, String findshScript, int count) {
		boolean isFinishTitle = false;
		findshScript = "," + findshScript;
		int i = 0;
		for (String[] splits : titleIng.getTitleList()) {
			boolean check = true;
			for (String split : splits) {
				String[] tjs = split.split("\\|");
				boolean checkt = false;
				for (String tj : tjs) {
					tj = "," + tj + ",";
					if (findshScript.indexOf(tj) != -1) {
						checkt = true;
						break;
					}
				}
				if (!checkt) {
					check = false;
					break;
				}
			}
			if (check && titleIng.getFinishValuetList().get(i).intValue() < titleIng.getTargetValueList().get(i).intValue()) {
				titleIng.getFinishValuetList().set(i, titleIng.getFinishValuetList().get(i) + count);
				if (titleIng.getFinishValuetList().get(i).intValue() > titleIng.getTargetValueList().get(i).intValue()) {
					titleIng.getFinishValuetList().set(i, titleIng.getTargetValueList().get(i));
				}
				this.updateTitleTargerStatus(titleIng);
			}
			i++;
		}
		boolean check = true;
		for (int y = 0; y < titleIng.getTargetValueList().size(); y++) {
			if (titleIng.getTargetValueList().get(y).intValue() > titleIng.getFinishValuetList().get(y).intValue()) {
				check = false;
				break;
			}
		}
		if (check) {
			titleIng.setStatus(Common.TITLE_STATUS_OBTAIN);
			StringBuffer targetStatus = new StringBuffer();
			for (int y = 0; y < titleIng.getTitleList().size(); y++) {
				String[] trs = titleIng.getTitleList().get(y);
				StringBuffer targer = new StringBuffer();
				for (int k = 0; k < trs.length; k++) {
					if (k == trs.length - 1) {
						targer.append(trs[k]);
					} else {
						targer.append(trs[k]).append(",");
					}
				}
				if (y == titleIng.getTitleList().size() - 1) {
					targetStatus.append(targer.toString()).append("=").append(titleIng.getFinishValuetList().get(y));
				} else {
					targetStatus.append(targer.toString()).append("=").append(titleIng.getFinishValuetList().get(y)).append("&");
				}
			}
			titleIng.setTargetStatus(targetStatus.toString());
			isFinishTitle = true;

		}
		return isFinishTitle;
	}

	/**
	 * 玩家完成任务，更新任务状态及发送任务奖励
	 * 
	 * @param player
	 *            玩家信息
	 * @param playerTask
	 *            中间表信息
	 * @param commitTask
	 *            提交任务协议
	 * @throws Exception
	 *             操作出错时抛出此异常
	 */
	public void updateTaskFinishStatus(PlayerTask taskIng, WorldPlayer player) throws Exception {
		List<RewardInfo> rewardList;
		if (taskIng.getTaskType() == Common.TASK_TYPE_DAY) {
			DayTask task = ServiceManager.getManager().getTaskService().getService().getDayTaskById(taskIng.getTaskId());
			rewardList = task.getRewardBySex(player.getPlayer().getSex().intValue());
		} else {
			Task task = ServiceManager.getManager().getTaskService().getService().getTaskById(taskIng.getTaskId());
			rewardList = task.getRewardBySex(player.getPlayer().getSex().intValue());
		}
		for (RewardInfo info : rewardList) {
			sendReward(player, info, taskIng);
		}
		taskIng.setEndTime((new Date()).getTime());
		updatePlayerTaskStatus(player, taskIng, Common.TASK_STATUS_FINISHED, true);
	}

	/**
	 * 发送奖励
	 * 
	 * @param info
	 * @throws Exception
	 * @return 返回奖励物品的数量
	 */
	public int sendReward(WorldPlayer player, RewardInfo info, PlayerTask taskIng) throws Exception {
		int count = getRewardAddition(player.getPlayer().getVipLevel(), info, taskIng.getUpLevel(), taskIng.getTaskType());

		switch (info.getItemId()) {
			case Common.DIAMONDID :
				ServiceManager.getManager().getPlayerService()
						.addTicket(player, count, 0, TradeService.ORIGIN_TASK, 0, "", taskIng.getTaskId() + "", "", "");
				break;
			case Common.GOLDID :
				ServiceManager.getManager().getPlayerService()
						.updatePlayerGold(player, count, SystemLogService.GSTASK, "任务id：" + taskIng.getTaskId());
				break;
			case Common.EXPID :
				ServiceManager.getManager().getPlayerService().updatePlayerEXP(player, count);
				break;
			case Common.HYZID :
				PlayerInfo playerInfo = player.getPlayerInfo();
				playerInfo.setActivity(playerInfo.getActivity() + count);
				player.updatePlayerInfo();
				if (ServiceManager.getManager().getTaskService().getService().hasActiveReward(player)) {
					player.updateButtonInfo(Common.BUTTON_ID_ACTIVE, true, 0);
				}
				// 记录成就
				ServiceManager.getManager().getTitleService().activity(player, playerInfo.getActivity());
				GameLogService.addActivity(player.getId(), player.getLevel(), taskIng.getTaskId(), count, playerInfo.getActivity());
				break;
			case Common.BADGEID :
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.playerGetItem(player.getId(), info.getItemId(), -1, -1, count, 2, null, 0, 0, 0);
				break;
			default :
				if (info.isAddDay()) {
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(player.getId(), info.getItemId(), -1, count, -1, 2, null, 0, 0, 0);
				} else {
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(player.getId(), info.getItemId(), -1, -1, info.getCount(), 2, null, 0, 0, 0);
				}
				break;
		}
		return count;
	}

	/**
	 * 获取加成后任务奖励数量
	 * 
	 * @param vipLevel
	 * @param info
	 * @param taskLevel
	 * @param taskType
	 * @return
	 */
	public static int getRewardAddition(int vipLevel, RewardInfo info, int taskLevel, byte taskType) {
		int count = info.getCount();
		if (taskType == Common.TASK_TYPE_DAY) {// 计算日常任务提升信息
			// 奖励=（数据表基础奖励*星级系数）*（1+系数）
			// 任务奖励=基础奖励*等级系数*星级系数*提升奖励系数
			count = (int) (count * getStarAddition(getTaskStar(vipLevel)) * (1 + getLevelAddition(taskLevel)));
		}
		return count;
	}

	public static int getLevelAddition(int count, int level) {
		return (int) (count * (1 + (level / 20f) * 0.5f));
	}

	/**
	 * 获取玩家任务星级
	 * 
	 * @param player
	 * @return
	 */
	public static int getTaskStar(int vipLevel) {
		if (vipLevel > 0) {
			if (vipLevel > 9) {
				return 5;
			} else if (vipLevel > 7) {
				return 4;
			} else if (vipLevel > 4) {
				return 3;
			} else if (vipLevel > 1) {
				return 2;
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}

	/**
	 * 获取任务星级奖励加成
	 * 
	 * @param star
	 * @return
	 */
	public static float getStarAddition(int star) {
		switch (star) {
			case 1 :
				return 1f;
			case 2 :
				return 1.2f;
			case 3 :
				return 1.5f;
			case 4 :
				return 2f;
			case 5 :
				return 2.5f;
			default :
				return 1f;
		}
	}

	/**
	 * 获取任务提升等级奖励加成
	 * 
	 * @param level
	 * @return
	 */
	public static float getLevelAddition(int level) {
		switch (level) {
			case 1 :
				return 0.2f;
			case 2 :
				return 0.6f;
			case 3 :
				return 1.2f;
			case 4 :
				return 2f;
			default :
				return 0f;
		}
	}

	/**
	 * 获取任务提升所需的钻石数量
	 * 
	 * @param diamond
	 * @param level
	 * @return
	 */
	public static int getUpLevelCost(int diamond, int level) {
		switch (level) {
			case 1 :
				return diamond;
			case 2 :
				return (int) (1.2 * diamond);
			case 3 :
				return (int) (1.5 * diamond);
			case 4 :
				return 2 * diamond;
			default :
				return 0;
		}
	}

	/**
	 * 获取可用称号列表
	 * 
	 * @return 可用任务列表
	 */
	public List<Title> getEffectiveTitleList() {
		return dao.getEffectiveTitleList();
	}

	/**
	 * 获取称号列表
	 * 
	 * @return 称号列表
	 */
	@SuppressWarnings("unchecked")
	public List<Title> getTitleList() {
		return this.getAll(Title.class);
	}

	/**
	 * 初始化正在进行中的任务对象
	 * 
	 * @param playerTask
	 *            任务与玩家中间表信息
	 * @param task
	 *            任务信息
	 * @return 正在进行中的任务对象
	 */
	public void initTaskIng(WorldPlayer player, PlayerTask taskIng, int playerId, int level) {
		if (taskIng.getStatus() == Common.TASK_STATUS_FINISHING) {
			Task task = ServiceManager.getManager().getTaskService().getTaskService().getTaskById(taskIng.getTaskId());
			if (task != null) {
				if (null == task.getTarget() || task.getTarget().equals("")) {
					updatePlayerTaskStatus(player, taskIng, Common.TASK_STATUS_SUBMIT, false);
					return;
				}
				// 重置任务相关信息
				for (int i = 0; i < taskIng.getTargetStatus().length; i++) {
					updatePlayerTaskTarget(player, taskIng, i, 0, false);
				}
			}
		}
	}

	/**
	 * 获取称号信息
	 * 
	 * @param titleId
	 *            称号ID
	 * @return 称号信息
	 */
	public Title getTitle(int titleId) {
		return (Title) this.get(Title.class, titleId);
	}

	/**
	 * 重新初始化默认任务列表(不含称号)
	 * 
	 * @param playerId
	 *            玩家信息
	 */
	public PlayerTaskTitle initCreateDefaultTaskList(Player player) {
		PlayerTaskTitle playerTaskTitle = dao.getPlayerTaskTitleByPlayerId(player.getId());
		// 初始化默认任务列表
		List<PlayerTask> playerTaskList = new ArrayList<PlayerTask>();
		// 初始任务
		List<Task> taskInfoList = ServiceManager.getManager().getTaskService().getTaskService().getAllTaskList();
		if (taskInfoList != null && !taskInfoList.isEmpty()) {
			for (Task obj : taskInfoList) {
				playerTaskList.add(new PlayerTask(obj.getId(), obj.getTaskType(), obj.getTargetValueList()));
			}
		}
		initialTask(null, playerTaskList, 1, player.getId());
		// initialDayTask(player.getTaskIngList(), player.getLevel(),
		// player.getLevel());
		// activateDayTask(player);
		if (playerTaskTitle == null) {
			// 保存任务与成就信息
			playerTaskTitle = new PlayerTaskTitle();
			// 初始化默认成就列表
			List<PlayerTitleVo> playerTitleVoList = new ArrayList<PlayerTitleVo>();
			List<Integer> titleIdList = dao.getEffectiveTitleIdList();
			if (titleIdList != null && !titleIdList.isEmpty()) {
				for (Integer titleId : titleIdList) {
					playerTitleVoList.add(new PlayerTitleVo(titleId));
				}
			}
			playerTaskTitle.setTitleList(SerializeUtil.serialize(playerTitleVoList));
			playerTaskTitle.setPlayer(player);
			playerTaskTitle.setTaskList(SerializeUtil.serialize(playerTaskList));
			this.save(playerTaskTitle);
		} else {
			playerTaskTitle.setPlayer(player);
			playerTaskTitle.setTaskList(SerializeUtil.serialize(playerTaskList));
			this.update(playerTaskTitle);
		}
		return playerTaskTitle;
	}

	/**
	 * 更新玩家的任务状态
	 * 
	 * @param player
	 *            角色对象
	 * @param task
	 *            任务对象
	 * @param status
	 *            更新的状态
	 * @param issyn
	 *            是否需要同步客户端
	 */
	public void updatePlayerTaskStatus(WorldPlayer player, PlayerTask task, byte status, boolean issyn) {
		task.setStatus(status);
		if (issyn) {
			synTaskStatus(player, task);
		}
		if ((task.getStatus() == Common.TASK_STATUS_SUBMIT || task.getStatus() == Common.TASK_STATUS_FINISHED)
				&& (task.getTaskType() == Common.TASK_TYPE_MAIN || task.getTaskType() == Common.TASK_TYPE_DAY)) {
			synCommitTaskNum(player);
		}
		GameLogService.taskStatusChange(player.getId(), player.getLevel(), task.getTaskId(), task.getTaskType(), task.getStatus());
	}

	/**
	 * 更新玩家的任务状态
	 * 
	 * @param player
	 *            角色对象
	 * @param task
	 *            任务对象
	 * @param index
	 *            目标索引 -1为直接复制完成目标值
	 * @param value
	 *            当前完成值
	 * @param issyn
	 *            是否需要同步客户端
	 */
	public void updatePlayerTaskTarget(WorldPlayer player, PlayerTask task, int index, int value, boolean issyn) {
		if (index < 0) {
			task.setTargetStatus(task.getTargetValue());
		} else {
			value = value < task.getTargetValue()[index] ? value : task.getTargetValue()[index];
			task.getTargetStatus()[index] = value;
		}
		if (issyn) {
			synTaskStatus(player, task);
		}
	}

	/**
	 * 同步玩家任务状态
	 * 
	 * @param player
	 * @param task
	 */
	private void synTaskStatus(WorldPlayer player, PlayerTask task) {
		if (null != player) {
			StatusChanged statusChanged = new StatusChanged();
			statusChanged.setTaskId(task.getTaskId());
			statusChanged.setTaskType(task.getTaskType());
			statusChanged.setStatus(task.getStatus());
			statusChanged.setTargetStatus(task.getTargetStatus());
			// System.out.println("taskId:" + task.getTaskId() + " TaskType:" +
			// task.getTaskType() + " SubType:" + task.getTaskSubType() +
			// " TaskStatus:" + task.getStatus() + " TargetStatus:" +
			// task.getTargetStatus()[0]);
			player.sendData(statusChanged);
		}
	}

	/**
	 * 同步玩家可提交任务数量
	 * 
	 * @param player
	 */
	public void synCommitTaskNum(WorldPlayer player) {
		if (null != player) {
			List<PlayerTask> ptList = player.getTaskIngList();
			int num = 0;
			for (PlayerTask pt : ptList) {
				if (pt.getStatus() == Common.TASK_STATUS_SUBMIT
						&& (pt.getTaskType() == Common.TASK_TYPE_MAIN || pt.getTaskType() == Common.TASK_TYPE_DAY)) {
					num++;
				}
			}
			GetCommitTaskNum gctn = new GetCommitTaskNum();
			gctn.setTaskNum(num);
			player.sendData(gctn);
		}
	}
}
