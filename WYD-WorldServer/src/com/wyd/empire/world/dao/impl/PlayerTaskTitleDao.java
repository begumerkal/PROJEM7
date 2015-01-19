package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.PlayerTaskTitle;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.bean.Title;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.dao.IPlayerTaskTitleDao;

/**
 * The DAO class for the TabPlayerTaskTitle entity.
 */
public class PlayerTaskTitleDao extends UniversalDaoHibernate implements IPlayerTaskTitleDao {
	public PlayerTaskTitleDao() {
		super();
	}

	/**
	 * 获取可用任务ID列表
	 * 
	 * @return 可用任务ID列表
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getEffectiveTaskIdList() {
		StringBuffer hsql = new StringBuffer();
		hsql.append("SELECT id, taskType FROM Task WHERE 1 = 1 ");
		hsql.append(" ORDER BY id");
		return this.getList(hsql.toString(), new Object[]{});
	}

	/**
	 * 获取可用称号ID列表
	 * 
	 * @return 可用称号ID列表
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getEffectiveTitleIdList() {
		StringBuffer hsql = new StringBuffer();
		hsql.append(" SELECT id FROM Title WHERE 1 = 1 ");
		return this.getList(hsql.toString(), new Object[]{});
	}

	/**
	 * 根据玩家ID获取玩家任务，称号信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 取玩家任务，称号信息
	 */
	public PlayerTaskTitle getPlayerTaskTitleByPlayerId(int playerId) {
		StringBuffer hsql = new StringBuffer();
		hsql.append(" FROM PlayerTaskTitle WHERE 1 = 1");
		hsql.append(" AND player.id = ? ");
		return (PlayerTaskTitle) this.getUniqueResult(hsql.toString(), new Object[]{playerId});
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
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM Task WHERE 1 = 1 ");
		String[] dates = key.split("\\|");
		for (int i = 0; i < dates.length; i++) {
			if (StringUtils.hasText(dates[i])) {
				switch (i) {
					case 0 :
						hql.append(" AND (taskName like '" + dates[0] + "%' or taskName like '%" + dates[0] + "' or taskName like '%"
								+ dates[0] + "%') ");
						break;
					case 1 :
						hql.append(" AND taskType = ? ");
						values.add(Byte.valueOf(dates[1]));
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hql.toString();
		hql.append(" ORDER BY id desc");
		return getPageList(hql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
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
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM Title WHERE 1 = 1 ");
		String[] dates = key.split("\\|");
		for (int i = 0; i < dates.length; i++) {
			if (StringUtils.hasText(dates[i])) {
				switch (i) {
					case 0 :
						hql.append(" AND  title like '%" + dates[0] + "%' ");
						break;
					case 1 :
						hql.append(" AND titleType = ? ");
						values.add(Integer.valueOf(dates[1]));
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hql.toString();
		hql.append(" ORDER BY id desc");
		return getPageList(hql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 根据等级获取当前会员排名
	 * 
	 * @param exp
	 *            等级
	 * @return 当前会员排名
	 */
	public long findCurrentRank(int exp) {
		StringBuffer hql = new StringBuffer();
		hql.append(" SELECT COUNT(*) FROM VipRecord v,Player p WHERE v.player.id = p.id ");
		hql.append(" AND v.vipExp >= ? ");
		hql.append(" AND p.areaId = ? ");
		return this.count(hql.toString(), new Object[]{exp, Server.config.getMachineCode()});
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
		return count > maxCount ? maxCount : count;
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
		hql.append("SELECT DATE_FORMAT(on_time,'%Y-%m-%d') FROM PlayerOnline WHERE 1 = 1 AND player.id = ? GROUP BY DATE_FORMAT(on_time,'%Y-%m-%d') ORDER BY DATE_FORMAT(on_time,'%Y-%m-%d') DESC");
		values.add(playerId);
		return this.getList(hql.toString(), values.toArray(), count);
	}

	/**
	 * 获取最后任务更新日期
	 * 
	 * @return 最后任务更新日期
	 */
	public Date getLastTaskUpdateTime() {
		StringBuffer hsql = new StringBuffer();
		hsql.append(" SELECT MAX(updateTime) FROM Task");
		return (Date) this.getUniqueResult(hsql.toString(), new Object[]{});
	}

	/**
	 * 获取称号最后更新日期
	 * 
	 * @return 称号最后更新日期
	 */
	public Date getLastTitleUpdateTime() {
		StringBuffer hsql = new StringBuffer();
		hsql.append(" SELECT MAX(updateTime) FROM Title WHERE status = ? ");
		return (Date) this.getUniqueResult(hsql.toString(), new Object[]{(byte) Common.STATUS_SHOW});
	}

	/**
	 * 获取可用任务列表
	 * 
	 * @return 可用任务列表
	 */
	@SuppressWarnings("unchecked")
	public List<Task> getEffectiveTaskList() {
		StringBuffer hsql = new StringBuffer();
		hsql.append(" FROM Task WHERE 1 = 1 ");
		hsql.append(" AND status = ? ");
		return this.getList(hsql.toString(), new Object[]{(byte) Common.STATUS_SHOW});
	}

	/**
	 * 获取可用称号列表
	 * 
	 * @return 可用任务列表
	 */
	@SuppressWarnings("unchecked")
	public List<Title> getEffectiveTitleList() {
		StringBuffer hsql = new StringBuffer();
		hsql.append(" FROM Title WHERE 1 = 1 ");
		hsql.append(" AND status = ? ");
		return this.getList(hsql.toString(), new Object[]{(byte) Common.STATUS_SHOW});
	}
}
