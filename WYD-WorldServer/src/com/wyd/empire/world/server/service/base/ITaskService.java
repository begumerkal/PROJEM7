package com.wyd.empire.world.server.service.base;

import java.util.List;
import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.ActiveReward;
import com.wyd.empire.world.bean.DayTask;
import com.wyd.empire.world.bean.PlayerGuide;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.bean.Rewardrecord;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabTask entity.
 */
public interface ITaskService extends UniversalManager {
	/**
	 * 更新玩家的任务(玩家升级，完成任务时调用)
	 * 
	 * @param player
	 */
	public void checkTask(WorldPlayer player);

	/**
	 * 同步玩家的任务列表
	 */
	public void synPlayerTaskList(WorldPlayer player);

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
	 * @param parenthesis
	 */
	public void playerTaskCheck(WorldPlayer worldPlayer, String findshScript, int count, int dayTaskType, List<Integer> subTypes,
			List<Integer> targets, List<Integer> counts, String parenthesis);

	/**
	 * 根据玩家角色ID，获取玩家连接登录次数
	 * 
	 * @param playerId
	 *            玩家角色ID
	 * @param maxCount
	 *            最大返回条数
	 * @return 玩家连续登录次数
	 */
	public int getLoingNumByPlayerId(int playerId, int maxCount);

	/**
	 * 保存领取记录
	 * 
	 * @param playerId
	 *            用户角色ID
	 */
	public void saveRecord(int playerId, int vipMark, int type);

	/**
	 * 检查今天是否已领取
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean checkIsGetREward(int playerId, int vipMark, int type);

	/**
	 * 获取玩家vip等级领取记录
	 * 
	 * @param playerId
	 * @param type
	 *            记录类型
	 * @return
	 */
	public List<Rewardrecord> getVipLvPackReceive(int playerId, int type);

	/**
	 * 删除今天所有领取记录
	 */
	public void deleteAllRecord();

	/**
	 * 获取玩家促销记录
	 */
	public void addPromotionRecord(int playerId, int itemId);

	/**
	 * 获取玩家剩余购买数
	 */
	public int remainPromotion(int playerId, int itemId, int maxCount);

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
	public PageList findAllTask(String key, int pageIndex, int pageSize);

	/**
	 * 获取玩家奖励信息
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerReward playerRewardInfo(Integer playerId);

	/**
	 * 保存玩家的签到信息
	 */
	public void savePlayerSignInfo(Integer playerId);

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
	public List<RewardInfo> sendSignReward(String reward, WorldPlayer player, int getway, int origin, String remark);

	/**
	 * 根据天数获得奖励
	 * 
	 * @param param
	 * @param type
	 *            奖励类型
	 *            0累计签到奖励，1连续签到奖励，2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励，6在线奖励，7在线抽奖奖励
	 * @return
	 */
	public Reward getRewardByParam(int param, int type);

	/**
	 * 获得奖励列表
	 * 
	 * @param type
	 *            奖励类型
	 *            0累计签到奖励，1连续签到奖励，2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励，6在线奖励，7在线抽奖奖励
	 * @return
	 */
	public List<Reward> getRewardList(int type);

	/**
	 * 获取在线抽奖奖励
	 * 
	 * @return
	 */
	public List<Reward> getLotteryRewardList();

	/**
	 * 记录玩家累计登录天数（玩家每天首次登录调用）
	 * 
	 * @param playerId
	 */
	public void playerLogin(int playerId);

	/**
	 * 根据多个ID删除每日奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByeveryDayIds(String ids);

	/**
	 * 初始化数据
	 */
	public void initData();

	/**
	 * 获取所有任务列表
	 * 
	 * @return
	 */
	public List<Task> getAllTaskList();

	/**
	 * 根据id获取指定任务
	 */
	public Task getTaskById(int taskId);

	/**
	 * 获取所有的每日任务列表
	 * 
	 * @return
	 */
	public List<DayTask> getAllDayTaskList();

	/**
	 * 根据id获取指定的每日任务
	 * 
	 * @param taskId
	 * @return
	 */
	public DayTask getDayTaskById(int taskId);

	/**
	 * 获取活跃度奖励列表
	 * 
	 * @return
	 */
	public List<ActiveReward> getActiveRewardList();

	/**
	 * 是否有可领取奖励
	 * 
	 * @param worldPlayer
	 * @return
	 */
	public boolean hasActiveReward(WorldPlayer worldPlayer);

	/**
	 * 检查某个任务是否为父任务
	 * 
	 * @param taskId
	 * @return
	 */
	public boolean isParentTask(int taskId);

	/**
	 * 获取玩家的新手教程进度
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerGuide> getPlayerGuideList(int playerId);

	/**
	 * 更新玩家新手教程进度
	 * 
	 * @param playerId
	 * @param guide
	 * @param step
	 */
	public void setPlayerGuide(int playerId, int guide, int step);

	/**
	 * 同步玩家可领取奖励数量
	 * 
	 * @param player
	 */
	public void synRewardNum(WorldPlayer player);
}