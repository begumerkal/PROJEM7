package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.ActiveReward;
import com.wyd.empire.world.bean.DayTask;
import com.wyd.empire.world.bean.PlayerGuide;
import com.wyd.empire.world.bean.PlayerOnline;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.bean.Rewardrecord;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.dao.ITaskDao;

/**
 * The DAO class for the TabTask entity.
 */
public class TaskDao extends UniversalDaoHibernate implements ITaskDao {
	private Map<Integer, Task> taskMap = null;
	private List<Task> taskList = null;
	private Map<Integer, DayTask> dayTaskMap = null;
	private List<DayTask> dayTaskList = null;
	private List<ActiveReward> activeRewardList = null; // 活跃度奖励信息
	private List<Reward> rewardList = null; // 签到奖励信息

	public TaskDao() {
		super();
	}

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		Map<Integer, Task> taskMap = new HashMap<Integer, Task>();
		this.taskList = getList("FROM Task ", new Object[]{});
		for (Task task : taskList) {
			task.initTargetData();
			taskMap.put(task.getId(), task);
		}
		this.taskMap = taskMap;

		Map<Integer, DayTask> dayTaskMap = new HashMap<Integer, DayTask>();
		this.dayTaskList = getList("FROM DayTask ", new Object[]{});
		for (DayTask task : dayTaskList) {
			task.initData();
			dayTaskMap.put(task.getId(), task);
		}
		this.dayTaskMap = dayTaskMap;

		this.activeRewardList = getList("FROM ActiveReward ", new Object[]{});
		this.rewardList = getList("FROM Reward ORDER BY param ASC", new Object[]{});
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
		int count = 1;
		List<Object> list = this.getLoginList(playerId, maxCount);
		if (list != null && !list.isEmpty()) {
			Date d1 = new Date();
			Date d2 = null;
			for (Object obj : list) {
				d2 = DateUtil.stringToDate(obj.toString());
				if (DateUtil.compareDateOnDay(d1, d2) == 0) {
					continue;
				} else if (DateUtil.compareDateOnDay(d1, d2) == 1) {
					count++;
				} else {
					break;
				}
				d1 = d2;
			}
		}
		count = count > maxCount ? maxCount : count;
		return count;
	}

	/**
	 * 根据角色ID，返回结果集最大限制数获取玩家登录日期列表
	 * 
	 * @param playerId
	 *            角色ID
	 * @param count
	 *            返回结果集最大限制数
	 * @return 玩家登录日期列表
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getLoginList(int playerId, int count) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("SELECT DATE_FORMAT(ontime,'%Y-%m-%d') FROM " + PlayerOnline.class.getSimpleName()
				+ " WHERE 1 = 1 AND player.id = ? GROUP BY DATE_FORMAT(ontime,'%Y-%m-%d') ORDER BY DATE_FORMAT(ontime,'%Y-%m-%d') DESC");
		values.add(playerId);
		return this.getList(hql.toString(), values.toArray(), count);
	}

	/**
	 * 根据用户ID，检查今天是否已领取
	 * 
	 * @param playerId
	 *            用户角色ID
	 * @return true: 今天已经领取过,false: 今天未领取过
	 */
	public boolean checkIsGetREward(int playerId, int vipMark, int receiveType) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("SELECT COUNT(*) FROM " + Rewardrecord.class.getSimpleName() + " WHERE 1=1 ");
		hql.append(" AND playerId = ? ");
		values.add(playerId);
		hql.append(" AND vipMark = ? ");
		values.add(vipMark);
		hql.append(" AND receiveType = ? ");
		values.add(receiveType);
		long size = this.count(hql.toString(), values.toArray());
		if (size > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取玩家vip等级领取记录
	 * 
	 * @param playerId
	 * @param type
	 *            记录类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Rewardrecord> getVipLvPackReceive(int playerId, int receiveType) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM Rewardrecord WHERE  playerId = ? AND receiveType = ? ");
		values.add(playerId);
		values.add(receiveType);
		List<Rewardrecord> list = this.getList(hql.toString(), values.toArray());
		return list;
	}

	/**
	 * 删除今天所有领取记录 (不包括vip等级领取记录) receiveType = 1 表示可以删除的 比如每日领取记录
	 */
	public void deleteAllRecord() {
		StringBuilder hql = new StringBuilder();
		String areaId = Server.config.getAreaId();
		hql.append("DELETE FROM Rewardrecord where receiveType = 1 and areaId = ?");
		execute(hql.toString(), new Object[]{areaId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Rewardrecord getRewardrecord(int playerId, int vipMark) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM Rewardrecord WHERE receiveType=1 AND playerId = ? AND vipMark = ? ");
		values.add(playerId);
		values.add(vipMark);
		List<Rewardrecord> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
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
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + Task.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] dates = key.split("\\|");
		for (int i = 0; i < dates.length; i++) {
			if (StringUtils.hasText(dates[i])) {
				switch (i) {
					case 0 :
						hql.append(" AND taskName like '%" + dates[0] + "%' ");
						break;
					case 1 :
						hql.append(" AND taskType = ? ");
						values.add(Byte.valueOf(dates[1]));
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hql.toString();
		hql.append(" order by id desc");
		return getPageList(hql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 获取玩家奖励信息
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerReward getPlayerRewardByPlayerId(int playerId) {
		StringBuffer hsql = new StringBuffer();
		hsql.append("FROM ");
		hsql.append(PlayerReward.class.getSimpleName());
		hsql.append(" where playerId=? order by id desc");
		@SuppressWarnings("unchecked")
		List<PlayerReward> prList = getList(hsql.toString(), new Object[]{playerId});
		if (null != prList && prList.size() > 0) {
			return prList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据天数获得奖励
	 * 
	 * @param param
	 * @param type
	 *            类型 type 类型 0累计签到奖励，1连续签到奖励，2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励
	 * @return
	 */
	public Reward getRewardByParam(int param, int type) {
		for (Reward sr : rewardList) {
			if (sr.getType() == type && sr.getParam() >= param) {
				return sr;
			}
		}
		return null;
	}

	/**
	 * 获得奖励列表
	 * 
	 * @param type
	 *            类型 0累计签到奖励，1连续签到奖励，2累积登录奖励，3登录目标奖励，4等级奖励，5等级目标奖励
	 * @return
	 */
	public List<Reward> getRewardList(int type) {
		List<Reward> srList = new ArrayList<Reward>();
		for (Reward sr : rewardList) {
			if (sr.getType() == type)
				srList.add(sr);
		}
		return srList;
	}

	/**
	 * 根据多个ID删除每日奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByeveryDayIds(String ids) {
		this.execute("DELETE " + Reward.class.getSimpleName() + " WHERE id in (" + ids + ")", new Object[]{});
		initData();
	}

	/**
	 * 获取指定类型的任务
	 * 
	 * @param type
	 *            0主线，1活跃度, 2日常
	 * @param type
	 *            任务类型
	 * @return
	 */
	public List<Task> getTaskListByType(byte type) {
		List<Task> taskList = new ArrayList<Task>();
		for (Task task : this.taskList) {
			if (task.getTaskType() == type) {
				taskList.add(task);
			}
		}
		return taskList;
	}

	/**
	 * 获取所有任务列表
	 * 
	 * @return
	 */
	public List<Task> getTaskList() {
		return taskList;
	}

	/**
	 * 根据id获取指定任务
	 */
	public Task getTaskById(int taskId) {
		return taskMap.get(taskId);
	}

	/**
	 * 获取所有的每日任务列表
	 * 
	 * @return
	 */
	public List<DayTask> getDayTaskList() {
		return new ArrayList<DayTask>(dayTaskList);
	}

	/**
	 * 根据id获取指定的每日任务
	 * 
	 * @param taskId
	 * @return
	 */
	public DayTask getDayTaskById(int taskId) {
		return dayTaskMap.get(taskId);
	}

	/**
	 * 获取活跃度奖励列表
	 * 
	 * @return
	 */
	public List<ActiveReward> getActiveRewardList() {
		return activeRewardList;
	}

	/**
	 * 获取玩家的新手教程进度
	 * 
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlayerGuide> getPlayerGuideByPlayerId(int playerId) {
		StringBuffer hsql = new StringBuffer();
		hsql.append("FROM ");
		hsql.append(PlayerGuide.class.getSimpleName());
		hsql.append(" where playerId=?");
		return getList(hsql.toString(), new Object[]{playerId});
	}
}