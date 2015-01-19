package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.task.GetRewardNum;
import com.wyd.empire.protocol.data.task.GetTaskList;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.ActiveReward;
import com.wyd.empire.world.bean.DayTask;
import com.wyd.empire.world.bean.PlayerGuide;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.bean.Rewardrecord;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.dao.ITaskDao;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ITaskService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.task.TaskKey;
import com.wyd.net.ProtocolFactory;

/**
 * The service class for the TabTask entity.
 */
public class TaskService extends UniversalManagerImpl implements ITaskService {
	private static Map<Integer, PlayerReward> playerSignMap = new HashMap<Integer, PlayerReward>();
	private static Map<Integer, List<PlayerGuide>> playerGuideMap = new HashMap<Integer, List<PlayerGuide>>();
	/**
	 * The dao instance injected by Spring.
	 */
	private ITaskDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "TaskService";

	public TaskService() {
		super();
	}

	/**
	 * Returns the singleton <code>ITaskService</code> instance.
	 */
	public static ITaskService getInstance(ApplicationContext context) {
		return (ITaskService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(ITaskDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public ITaskDao getDao() {
		return this.dao;
	}

	/**
	 * 更新玩家的任务(玩家升级，完成任务时调用)
	 * 
	 * @param player
	 */
	public void checkTask(WorldPlayer player) {
		int initCount = ServiceManager.getManager().getPlayerTaskTitleService()
				.initialTask(player, player.getTaskIngList(), player.getLevel(), player.getId());
		// 如果有新任务触发或者玩家提升到61级开发日常任务时推送
		if (player != null && (initCount > 0 || player.getLevel() == 61)) {
			synPlayerTaskList(player);
		}
	}

	/**
	 * 同步玩家的任务列表
	 */
	public void synPlayerTaskList(WorldPlayer player) {
		if (player != null) {
			// 更新任务列表
			ConnectSession session = player.getConnectSession();
			Client client = player.getClient();
			if (null != session && null != client) {
				GetTaskList gtl = new GetTaskList();
				gtl.setHandlerSource(session);
				gtl.setSessionId(client.getSessionId());
				try {
					ProtocolFactory.getDataHandler(GetTaskList.class).handle(gtl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 更新任务完成情况
	 * 
	 * @param worldPlayer
	 * @param findshScript
	 * @param count
	 * @param dayTaskType
	 * @param subTypes
	 * @param targets
	 * @param counts
	 */
	public void playerTaskCheck(WorldPlayer worldPlayer, String findshScript, int count, int dayTaskType, List<Integer> subTypes,
			List<Integer> targets, List<Integer> counts, String parenthesis) {
		ServiceManager.getManager().getSimpleThreadPool()
				.execute(createTask(worldPlayer, findshScript, count, dayTaskType, subTypes, targets, counts, parenthesis));
	}

	/**
	 * 更新任务完成情况改由独立线程执行
	 * 
	 * @param worldPlayer
	 * @param findshScript
	 * @param count
	 * @return
	 */
	private Runnable createTask(WorldPlayer worldPlayer, String findshScript, int count, int dayTaskType, List<Integer> subTypes,
			List<Integer> targets, List<Integer> counts, String parenthesis) {
		return new TaskCheckThread(worldPlayer, findshScript, count, dayTaskType, subTypes, targets, counts, parenthesis);
	}

	public class TaskCheckThread implements Runnable {
		private WorldPlayer worldPlayer;
		private String findshScript;
		private int count;
		private int dayTaskType;
		private List<Integer> subTypes;
		private List<Integer> targets;
		private List<Integer> counts;
		private String parenthesis;

		public TaskCheckThread(WorldPlayer worldPlayer, String findshScript, int count, int dayTaskType, List<Integer> subTypes,
				List<Integer> targets, List<Integer> counts, String parenthesis) {
			this.findshScript = findshScript;
			this.worldPlayer = worldPlayer;
			this.dayTaskType = dayTaskType;
			this.parenthesis = parenthesis;
			this.subTypes = subTypes;
			this.targets = targets;
			this.counts = counts;
			this.count = count;
		}

		@Override
		public void run() {
			if (null != findshScript) {
				ServiceManager.getManager().getPlayerTaskTitleService().playerTaskCheck(worldPlayer, findshScript, count);
			}
			// 角色等级大于60才能进行日常任务
			if (0 != dayTaskType && worldPlayer.getLevel() > 60) {
				ServiceManager.getManager().getPlayerTaskTitleService()
						.playerTaskCheck(worldPlayer, dayTaskType, subTypes, targets, counts, parenthesis);
			}
			if (dayTaskType == TaskKey.DAY_TASK_ID_BATTLE) {
				ServiceManager.getManager().getPlayerTaskTitleService().initialOneTimesTask(worldPlayer);
			}
		}
	}

	/**
	 * 将未完成的一次性战斗任务初始化
	 * 
	 * @param player
	 */
	public void initialOneTimesTask(WorldPlayer player) {
		ServiceManager.getManager().getPlayerTaskTitleService().initialOneTimesTask(player);
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
	 * 保存领取记录
	 * 
	 * @param playerId
	 *            用户角色ID
	 */
	public void saveRecord(int playerId, int vipMark, int receiveType) {
		Rewardrecord rewardrecord = new Rewardrecord();
		rewardrecord.setTime(new Date());
		rewardrecord.setPlayerId(playerId);
		rewardrecord.setVipMark(vipMark);
		rewardrecord.setAreaId(Server.config.getAreaId());
		rewardrecord.setReceiveType(receiveType);
		this.save(rewardrecord);
	}

	/**
	 * 获取玩家促销记录
	 */
	@Override
	public void addPromotionRecord(int playerId, int itemId) {
		final int VIPMARK = 2;
		Rewardrecord rewardrecord = dao.getRewardrecord(playerId, VIPMARK);
		JSONObject json;
		String item = String.valueOf(itemId);
		if (rewardrecord == null) {
			rewardrecord = new Rewardrecord();
			rewardrecord.setTime(new Date());
			rewardrecord.setPlayerId(playerId);
			rewardrecord.setVipMark(VIPMARK);
			rewardrecord.setAreaId(Server.config.getAreaId());
			Map<String, Integer> promotionMap = new HashMap<String, Integer>();
			promotionMap.put(item, 1);
			json = JSONObject.fromObject(promotionMap);
		} else {
			json = JSONObject.fromObject(rewardrecord.getRemark());
			if (json.containsKey(item)) {
				Integer count = json.getInt(item);
				json.put(item, count + 1);
			} else {
				json.put(item, 1);
			}
		}
		rewardrecord.setRemark(json.toString());
		dao.saveOrUpdate(rewardrecord);
	}

	/**
	 * 获取玩家剩余购买数
	 */
	@Override
	public int remainPromotion(int playerId, int itemId, int maxCount) {
		final int VIPMARK = 2;
		Rewardrecord rewardrecord = dao.getRewardrecord(playerId, VIPMARK);
		String item = String.valueOf(itemId);
		if (rewardrecord == null) {
			return maxCount;
		} else {
			JSONObject json = JSONObject.fromObject(rewardrecord.getRemark());
			if (json.containsKey(item)) {
				Integer count = json.getInt(item);
				return maxCount - count;
			} else {
				return maxCount;
			}
		}
	}

	/**
	 * 检查今天是否已领取
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean checkIsGetREward(int playerId, int vipMark, int type) {
		return dao.checkIsGetREward(playerId, vipMark, type);
	}

	/**
	 * 获取玩家vip等级领取记录
	 * 
	 * @param playerId
	 * @param type
	 *            记录类型
	 * @return
	 */
	public List<Rewardrecord> getVipLvPackReceive(int playerId, int type) {
		return dao.getVipLvPackReceive(playerId, type);
	}

	/**
	 * 删除今天所有领取记录
	 */
	public void deleteAllRecord() {
		dao.deleteAllRecord();
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
	 * @return
	 */
	public PageList findAllTask(String key, int pageIndex, int pageSize) {
		return dao.findAllTask(key, pageIndex, pageSize);
	}

	/**
	 * 获取玩家奖励信息
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerReward playerRewardInfo(Integer playerId) {
		PlayerReward playerSign = playerSignMap.get(playerId);
		if (null == playerSign) {
			playerSign = dao.getPlayerRewardByPlayerId(playerId);
			if (null == playerSign) {
				playerSign = new PlayerReward();
				playerSign.setPlayerId(playerId);
				playerSign.initSignData();
				playerSign = (PlayerReward) save(playerSign);
			}
			playerSignMap.put(playerId, playerSign);
		}
		if (playerSign.getSignMonth() != DateUtil.getCurrentMonth()) {
			playerSign.initSignData();
		}
		return playerSign;
	}

	/**
	 * 保存玩家的签到信息
	 */
	public void savePlayerSignInfo(Integer playerId) {
		PlayerReward playerSign = playerSignMap.get(playerId);
		if (null != playerSign) {
			update(playerSign);
		}
	}

	/**
	 * 发送奖励
	 * 
	 * @param reward
	 *            奖励脚本
	 * @param player
	 *            奖励角色
	 * @param getway
	 *            物品获得途径 详见：saveGetItemRecord
	 * @param origin
	 *            钻石获取标识
	 * @param remark
	 *            附加信息
	 * @return
	 */
	public List<RewardInfo> sendSignReward(String reward, WorldPlayer player, int getway, int origin, String remark) {
		List<RewardInfo> riList = ServiceUtils.getRewardInfo(reward, player.getPlayer().getSex().intValue());
		// 获得奖励列表
		try {
			for (RewardInfo info : riList) {
				switch (info.getItemId()) {
					case Common.DIAMONDID :
						ServiceManager.getManager().getPlayerService().addTicket(player, info.getCount(), 0, origin, 0, "", remark, "", "");
						break;
					case Common.GOLDID :
						ServiceManager.getManager().getPlayerService().updatePlayerGold(player, info.getCount(), remark, "-- " + " --");
						break;
					case Common.EXPID :
						ServiceManager.getManager().getPlayerService().updatePlayerEXP(player, info.getCount());
						break;
					case Common.HYZID :
						PlayerInfo playerInfo = player.getPlayerInfo();
						playerInfo.setActivity(playerInfo.getActivity() + info.getCount());
						player.updatePlayerInfo();
						if (ServiceManager.getManager().getTaskService().getService().hasActiveReward(player)) {
							player.updateButtonInfo(Common.BUTTON_ID_ACTIVE, true, 0);
						}
						// 记录成就
						ServiceManager.getManager().getTitleService().activity(player, playerInfo.getActivity());
						GameLogService.addActivity(player.getId(), player.getLevel(), 0, info.getCount(), playerInfo.getActivity());
						break;
					default :
						if (info.isAddDay()) {
							ServiceManager.getManager().getPlayerItemsFromShopService()
									.playerGetItem(player.getId(), info.getItemId(), -1, info.getCount(), -1, getway, null, 0, 0, 0);
						} else {
							ServiceManager.getManager().getPlayerItemsFromShopService()
									.playerGetItem(player.getId(), info.getItemId(), -1, -1, info.getCount(), getway, null, 0, 0, 0);
						}
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return riList;
	}

	/**
	 * 根据天数获得奖励
	 * 
	 * @param param
	 * @param type
	 *            奖励类型
	 *            0累计签到奖励，1连续签到奖励，2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励，6在线奖励，7在线抽奖奖励
	 * @return
	 */
	public Reward getRewardByParam(int param, int type) {
		return dao.getRewardByParam(param, type);
	}

	/**
	 * 获得奖励列表
	 * 
	 * @param type
	 *            奖励类型
	 *            0累计签到奖励，1连续签到奖励，2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励，6在线奖励，7在线抽奖奖励
	 * @return
	 */
	public List<Reward> getRewardList(int type) {
		return dao.getRewardList(type);
	}

	/**
	 * 获取在线抽奖奖励
	 * 
	 * @return
	 */
	public List<Reward> getLotteryRewardList() {
		List<Reward> rewardList = dao.getRewardList(7);
		int base = 0;
		for (Reward reward : rewardList) {
			base += Integer.parseInt(reward.getRewardParam());
		}
		List<Reward> rList = new ArrayList<Reward>();
		for (int i = 0; i < 3; i++) {
			int random = ServiceUtils.getRandomNum(0, base);
			int br = 0;
			for (Reward reward : rewardList) {
				int pp = Integer.parseInt(reward.getRewardParam());
				br += pp;
				if (random < br) {
					rList.add(reward);
					base -= pp;
					rewardList.remove(reward);
					break;
				}
			}
		}
		return rList;
	}

	/**
	 * 记录玩家累计登录天数（玩家每天首次登录调用）
	 * 
	 * @param playerId
	 */
	public void playerLogin(int playerId) {
		PlayerReward playerReward = playerRewardInfo(playerId);
		playerReward.setLoginDays(playerReward.getLoginDays() + 1);
		savePlayerSignInfo(playerId);
	}

	/**
	 * 根据多个ID删除每日奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByeveryDayIds(String ids) {
		dao.deleteByeveryDayIds(ids);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}

	public Task getTaskById(int taskId) {
		return dao.getTaskById(taskId);
	}

	public List<Task> getAllTaskList() {
		return dao.getTaskList();
	}

	public DayTask getDayTaskById(int taskId) {
		return dao.getDayTaskById(taskId);
	}

	public List<DayTask> getAllDayTaskList() {
		return dao.getDayTaskList();
	}

	/**
	 * 获取活跃度奖励列表
	 * 
	 * @return
	 */
	public List<ActiveReward> getActiveRewardList() {
		return dao.getActiveRewardList();
	}

	/**
	 * 是否有可领取奖励
	 * 
	 * @param worldPlayer
	 * @return
	 */
	public boolean hasActiveReward(WorldPlayer worldPlayer) {
		PlayerInfo playerInfo = worldPlayer.getPlayerInfo();
		List<ActiveReward> activeRewardList = getActiveRewardList();
		for (int i = 0; i < activeRewardList.size(); i++) {
			ActiveReward activeReward = activeRewardList.get(i);
			if (!playerInfo.isActiveReward(i) && playerInfo.getActivity() >= activeReward.getActivityDemand()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查某个任务是否为父任务
	 * 
	 * @param taskId
	 * @return
	 */
	public boolean isParentTask(int taskId) {
		List<Task> taskList = dao.getTaskList();
		for (Task task : taskList) {
			if (null != task.getParentId() && task.getParentId().length() > 0) {
				String[] ids = task.getParentId().split(",");
				for (String id : ids) {
					if (id.equals(taskId + "")) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取玩家的新手教程进度
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerGuide> getPlayerGuideList(int playerId) {
		List<PlayerGuide> pgList = playerGuideMap.get(playerId);
		if (null == pgList) {
			pgList = dao.getPlayerGuideByPlayerId(playerId);
			if (null == pgList)
				pgList = new ArrayList<PlayerGuide>();
			playerGuideMap.put(playerId, pgList);
		}
		return pgList;
	}

	/**
	 * 更新玩家新手教程进度
	 * 
	 * @param playerId
	 * @param guide
	 * @param step
	 */
	public void setPlayerGuide(int playerId, int guide, int step) {
		List<PlayerGuide> pgList = getPlayerGuideList(playerId);
		PlayerGuide playerGuide = null;
		for (PlayerGuide pg : pgList) {
			if (pg.getGuide() == guide) {
				playerGuide = pg;
				break;
			}
		}
		if (null != playerGuide) {
			playerGuide.setStep(step);
			dao.update(playerGuide);
		} else {
			playerGuide = new PlayerGuide();
			playerGuide.setPlayerId(playerId);
			playerGuide.setGuide(guide);
			playerGuide.setStep(step);
			playerGuide = (PlayerGuide) dao.save(playerGuide);
			pgList.add(playerGuide);
		}
	}

	/**
	 * 同步玩家可领取奖励数量
	 * 
	 * @param player
	 */
	public void synRewardNum(WorldPlayer player) {
		if (null == player)
			return;
		PlayerReward rewardInfo = playerRewardInfo(player.getId());
		int signRewardNum = 0;
		if (!rewardInfo.isSign(DateUtil.getCurrentDay())) {
			signRewardNum++;
		}
		List<Reward> srList = getRewardList(0);
		for (Reward sr : srList) {
			if (rewardInfo.getSignDayList().size() >= sr.getParam() && !rewardInfo.isSignReward(sr.getParam())) {
				signRewardNum++;
			}
		}
		int loginRewardNum = 0;
		srList = getRewardList(2);
		srList.addAll(getRewardList(3));
		for (Reward sr : srList) {
			if (rewardInfo.getLoginDays() < sr.getParam())
				continue;
			if (sr.getType() == 2 && !rewardInfo.isLoginReward(sr.getParam())) {
				loginRewardNum++;
			}
			if (sr.getType() == 3 && !rewardInfo.isLoginTargetReward(sr.getParam())) {
				loginRewardNum++;
			}
		}
		int levelRewardNum = 0;
		srList = getRewardList(4);
		srList.addAll(getRewardList(5));
		for (Reward sr : srList) {
			if (player.getLevel() < sr.getParam())
				continue;
			if (sr.getType() == 4 && !rewardInfo.isLevelReward(sr.getParam())) {
				levelRewardNum++;
			}
			if (sr.getType() == 5 && !rewardInfo.isLevelTargetReward(sr.getParam())) {
				levelRewardNum++;
			}
		}
		int onlineRewardNum = 0;
		if (rewardInfo.getOnlineTime() > 0 && (rewardInfo.getOnlineTime() - System.currentTimeMillis()) < 1) {
			onlineRewardNum++;
		}
		onlineRewardNum += rewardInfo.getLotteryTimes();
		GetRewardNum getRewardNum = new GetRewardNum();
		getRewardNum.setSignRewardNum(signRewardNum);
		getRewardNum.setLoginRewardNum(loginRewardNum);
		getRewardNum.setLevelRewardNum(levelRewardNum);
		getRewardNum.setOnlineRewardNum(onlineRewardNum);
		player.sendData(getRewardNum);
		// System.out.println("playerId:" + player.getId() + " signRewardNum:" +
		// signRewardNum + " loginRewardNum:" + loginRewardNum +
		// " levelRewardNum:" + levelRewardNum + " onlineRewardNum:" +
		// onlineRewardNum);
	}
}