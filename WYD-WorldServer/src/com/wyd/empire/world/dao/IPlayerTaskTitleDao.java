package com.wyd.empire.world.dao;

import java.util.Date;
import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.PlayerTaskTitle;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.bean.Title;

/**
 * The DAO interface for the TabPlayerTaskTitle entity.
 */
public interface IPlayerTaskTitleDao extends UniversalDao {
	/**
	 * 获取可用任务ID列表
	 * 
	 * @return 可用任务ID列表
	 */
	public List<Object> getEffectiveTaskIdList();

	/**
	 * 获取可用称号ID列表
	 * 
	 * @return 可用称号ID列表
	 */
	public List<Integer> getEffectiveTitleIdList();

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
	 *            等级
	 * @return 当前会员排名
	 */
	public long findCurrentRank(int exp);

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
	 * 获取可用称号列表
	 * 
	 * @return 可用任务列表
	 */
	public List<Title> getEffectiveTitleList();
}
