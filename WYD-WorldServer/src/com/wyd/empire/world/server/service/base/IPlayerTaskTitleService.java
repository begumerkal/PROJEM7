package com.wyd.empire.world.server.service.base;

import java.util.Date;
import java.util.List;
import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerTaskTitle;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.bean.Title;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.empire.world.title.PlayerTitleVo;
import com.wyd.empire.world.title.TitleIng;

/**
 * The service interface for the TabPlayerTaskTitle entity.
 */
public interface IPlayerTaskTitleService extends UniversalManager {
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
	public boolean isTriggerTask(int taskId, List<PlayerTask> playerTaskList, int level);

	/**
	 * 获取父ID列表
	 * 
	 * @param parentTaskId
	 *            父ID字符串
	 * @return 取父ID列表
	 */
	public List<Integer> getParentTaskIdList(String parentTaskId);

	/**
	 * 创建默认任务列表
	 * 
	 * @param player
	 *            玩家信息
	 */
	public PlayerTaskTitle createDefaultTaskAndTitleList(WorldPlayer player);

	/**
	 * 创建玩家的任务列表
	 * 
	 * @return
	 */
	public List<PlayerTask> createPlayerTask();

	/**
	 * 重新初始化默认任务列表(不含称号)
	 * 
	 * @param playerId
	 *            玩家信息
	 */
	public PlayerTaskTitle initCreateDefaultTaskList(Player player);

	/**
	 * 根据玩家ID获取玩家任务，称号信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 取玩家任务，称号信息
	 */
	public PlayerTaskTitle getPlayerTaskTitleByPlayerId(int playerId);

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
	public PageList findAllTask(String key, int pageIndex, int pageSize);

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
	public PageList findAllTitle(String key, int pageIndex, int pageSize);

	/**
	 * 根据等级获取当前会员排名
	 * 
	 * @param exp
	 *            经验
	 * @return 当前会员排名
	 */
	public int findCurrentRank(int exp);

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
	 * 登录和每天凌晨更新玩家日常活动任务
	 * 
	 * @param player
	 *            玩家信息
	 * @param isCheck
	 *            是否需要检测缓存中的任务
	 */
	public void initialPlayerTask(WorldPlayer worldPlayer);

	/**
	 * 激活每日任务
	 */
	public int activateDayTask(WorldPlayer player, List<PlayerTask> playerTaskList);

	/**
	 * 保存玩家未更新到数据库的任务，成就缓存信息
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 */
	public void savePlayerTaskTitle(WorldPlayer worldPlayer);

	/**
	 * 保存成就更新列表
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 * @return 目前玩家所有成就列表
	 */
	public List<PlayerTitleVo> savePlayerTitle(WorldPlayer worldPlayer);

	/**
	 * 保存玩家任务列表信息
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 * @throws Exception
	 *             复制对象内容出错时抛出此异常
	 * @return 任务列表
	 */
	public List<PlayerTask> savePlayerTask(WorldPlayer worldPlayer) throws Exception;

	/**
	 * 获取最后任务更新日期
	 * 
	 * @return 最后任务更新日期
	 */
	public Date getLastTaskUpdateTime();

	/**
	 * 获取称号最后更新日期
	 * 
	 * @return 称号最后更新日期
	 */
	public Date getLastTitleUpdateTime();

	/**
	 * 获取可用任务列表
	 * 
	 * @return 可用任务列表
	 */
	public List<Task> getEffectiveTaskList();

	/**
	 * 更新任务完成情况
	 * 
	 * @param playerTask
	 *            玩家任务对应信息
	 * @param findshScript
	 *            任务脚本
	 * @param count
	 *            数量
	 */
	public void playerTaskCheck(WorldPlayer worldPlayer, String findshScript, int count);

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
			List<Integer> count, String parenthesis);

	/**
	 * 将未完成的一次性战斗任务初始化
	 * 
	 * @param player
	 *            玩家信息
	 */
	public void initialOneTimesTask(WorldPlayer player);

	/**
	 * 将未完成的一次性成就重新初始化
	 * 
	 * @param worldplayer
	 *            玩家信息
	 */
	public void initialOneTimesTitle(WorldPlayer worldplayer);

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
	public boolean updatePlayerTask(WorldPlayer worldPlayer, PlayerTask taskIng, String findshScript, int count);

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
	public void titleTaskCheck(WorldPlayer worldPlayer, String findshScript, int count);

	/**
	 * 玩家完成任务，更新任务状态及发送任务奖励
	 * 
	 * @param player
	 *            玩家信息
	 * @param taskIng
	 *            中间表信息
	 * @param commitTask
	 *            提交任务协议
	 * @throws Exception
	 *             操作出错时抛出此异常
	 */
	public void updateTaskFinishStatus(PlayerTask taskIng, WorldPlayer worldPlayer) throws Exception;

	/**
	 * 获取可用称号列表
	 * 
	 * @return 可用任务列表
	 */
	public List<Title> getEffectiveTitleList();

	/**
	 * 获取称号列表
	 * 
	 * @return 称号列表
	 */
	public List<Title> getTitleList();

	/**
	 * 获取正在进行中的任务对象
	 * 
	 * @param playerTaskVo
	 *            任务与玩家中间表信息
	 * @param task
	 *            任务信息
	 * @return 正在进行中的任务对象
	 */
	public void initTaskIng(WorldPlayer player, PlayerTask taskIng, int playerId, int level);

	/**
	 * 初始化主线或支线任务
	 * 
	 * @param playerTaskVoList
	 *            玩家任务列表
	 * @param level
	 *            玩家等级
	 * @return 触发任务数量
	 */
	public int initialTask(WorldPlayer player, List<PlayerTask> playerTaskList, int level, int playerId);

	/**
	 * 获取正在进行的单个称号信息
	 * 
	 * @param playerTitleVo
	 *            称号完成情况信息
	 * @return
	 */
	public TitleIng getTitleIng(Title title, PlayerTitleVo playerTitleVo);

	/**
	 * 获取称号信息
	 * 
	 * @param titleId
	 *            称号ID
	 * @return 称号信息
	 */
	public Title getTitle(int titleId);

	/**
	 * 更新玩家的任务状态
	 * 
	 * @param player
	 *            角色对象
	 * @param playerTask
	 *            任务对象
	 * @param status
	 *            更新的状态
	 * @param issyn
	 *            是否需要同步客户端
	 */
	public void updatePlayerTaskStatus(WorldPlayer player, PlayerTask playerTask, byte status, boolean issyn);

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
	public void updatePlayerTaskTarget(WorldPlayer player, PlayerTask task, int index, int value, boolean issyn);

	/**
	 * 同步玩家可提交任务数量
	 * 
	 * @param player
	 */
	public void synCommitTaskNum(WorldPlayer player);
}
