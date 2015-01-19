package com.wyd.empire.world.dao;

import java.util.List;
import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.ActiveReward;
import com.wyd.empire.world.bean.DayTask;
import com.wyd.empire.world.bean.PlayerGuide;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.bean.Rewardrecord;
import com.wyd.empire.world.bean.Task;

/**
 * The DAO interface for the TabTask entity.
 */
public interface ITaskDao extends UniversalDao {
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
	 * 根据用户ID，检查今天是否已领取
	 * 
	 * @param playerId
	 *            用户角色ID
	 * @return true: 今天已经登录过,false: 今天未登录过
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

	public Rewardrecord getRewardrecord(int playerId, int vipMark);

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
	public PlayerReward getPlayerRewardByPlayerId(int playerId);

	/**
	 * 根据天数获得奖励
	 * 
	 * @param param
	 * @param type
	 *            类型 type 类型 0累计签到奖励，1连续签到奖励，2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励
	 * @return
	 */
	public Reward getRewardByParam(int param, int type);

	/**
	 * 获得奖励列表
	 * 
	 * @param type
	 *            类型 0累计签到奖励，1连续签到奖励，2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励
	 * @return
	 */
	public List<Reward> getRewardList(int type);

	/**
	 * 根据多个ID删除每日奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByeveryDayIds(String ids);

	/**
	 * 获取指定类型的任务
	 * 
	 * @param type
	 *            0主线，1活跃度, 2日常
	 * @return
	 */
	public List<Task> getTaskListByType(byte type);

	/**
	 * 初始化数据
	 */
	public void initData();

	/**
	 * 获取所有任务列表
	 * 
	 * @return
	 */
	public List<Task> getTaskList();

	/**
	 * 根据id获取指定任务
	 */
	public Task getTaskById(int taskId);

	/**
	 * 获取所有的每日任务列表
	 * 
	 * @return
	 */
	public List<DayTask> getDayTaskList();

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
	 * 获取玩家的新手教程进度
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerGuide> getPlayerGuideByPlayerId(int playerId);
}