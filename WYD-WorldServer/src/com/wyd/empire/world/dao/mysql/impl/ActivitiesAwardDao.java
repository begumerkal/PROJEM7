package com.wyd.empire.world.dao.mysql.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.util.StringUtils;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.mysql.IActivitiesAwardDao;
import com.wyd.empire.world.entity.mysql.ActivitiesAward;
import com.wyd.empire.world.entity.mysql.LogActivitiesAward;
import com.wyd.empire.world.service.factory.ServiceManager;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class ActivitiesAwardDao extends UniversalDaoHibernate implements IActivitiesAwardDao {
	public ActivitiesAwardDao() {
		super();
	}

	/**
	 * 获取活动奖励列表
	 * 
	 * @param key
	 *            关键字
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findAllActivity(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM " + ActivitiesAward.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] params = key.split("\\|");
		for (int i = 0; i < params.length; i++) {
			if (StringUtils.hasText(params[i])) {
				switch (i) {
					case 0 :
						break;
					default :
						break;
				}
			}
		}
		hsql.append(" AND areaId = ? ");
		values.add(WorldServer.config.getAreaId());
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 根据多个活动奖励ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteActivityByIds(String ids) {
		this.execute("DELETE " + ActivitiesAward.class.getSimpleName() + " WHERE id in (" + ids + ") ", new Object[]{});
	}

	/**
	 * 根据区域号查询出活动奖励记录
	 * 
	 * @param areaId
	 *            区域号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActivitiesAward> findAllActivity(String areaId) {
		return getList("FROM " + ActivitiesAward.class.getSimpleName() + " WHERE areaId=? AND isSend=?", new Object[]{areaId,
				1});
	}

	/**
	 * 根据时间和玩家ID查询出是否已经发放奖励
	 * 
	 * @param dateTime
	 *            时间日期
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long isSend(String dateTime, int playerId, String activityName) {
		Object obj = this.getUniqueResultBySql("SELECT COUNT(*) From log_activities_award WHERE DATE(create_time)='" + dateTime
				+ "' AND player_id=? AND activity_name=?", new Object[]{playerId, activityName});
		return Long.valueOf(obj.toString());
	}

	/**
	 * 根据充值记录表判断玩家是否已经发放过奖励
	 * 
	 * @param playerId
	 *            玩家id
	 * @param playerBillId
	 *            充值记录表id
	 * @return
	 */
	public long isGive(int playerId, int playerBillId) {
		Object obj = this.getUniqueResultBySql("SELECT COUNT(*) From log_activities_award WHERE player_id=? AND player_bill_id=?",
				new Object[]{playerId, playerBillId});
		return Long.valueOf(obj.toString());
	}

	/**
	 * Gm工具 根据条件查询出所有活动奖励日志（提供分页） 没有参数用null替代，此key中必须有5个参数
	 * 的长度，否则会报错例如“你好|null|null|null|null”
	 * 
	 * @param activityName
	 *            活动名称
	 * @param playerId
	 *            玩家ID
	 * @param stime
	 *            时间日期
	 * @param etime
	 *            时间日期
	 * @param isSend
	 *            是否发送（默认为""）
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findLogActivity(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hsql.append(" FROM  " + LogActivitiesAward.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] params = key.split("\\|");
		boolean stimeNull = true; // 开始时间为空
		boolean etimeNull = true; // 介绍时间为空
		if (!params[0].equals(null) && !params[0].equals("") && !params[0].equals(" ")) {
			hsql.append(" AND (activityName like '" + params[0] + "%' or activityName like '%" + params[0] + "' or activityName like '%"
					+ params[0] + "%') ");
		}
		if (!params[1].equals(null) && !params[1].equals("") && !params[1].equals(" ") && ServiceUtils.isNumeric(params[1])) {
			hsql.append(" AND playerId = ? ");
			values.add(Integer.parseInt(params[1]));
		}
		if (!params[2].equals(null) && !params[2].equals("") && !params[2].equals(" ")) {
			stimeNull = false;
		}
		if (!params[3].equals(null) && !params[3].equals("") && !params[3].equals(" ")) {
			etimeNull = false;
		}
		try {
			if (!stimeNull && !etimeNull) {
				hsql.append(" AND startTime BETWEEN ? and ? ");
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[2]));
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[3]));
			} else if (!stimeNull) {
				hsql.append(" AND startTime = ? ");
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[2]));
			} else if (!etimeNull) {
				hsql.append(" AND endTime = ? ");
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[3]));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!params[4].equals(null) && !params[4].equals("") && !params[4].equals(" ")) {
			hsql.append(" AND isSend = ? ");
			values.add(params[4]);
		}
		hsql.append(" and  areaId = ");
		hsql.append("'" + ServiceManager.getManager().getConfiguration().getString("areaid") + "'");
		hsql.append(" ORDER BY id DESC ");
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * Gm工具 根据条件查询出所有活动奖励日志总数 没有参数用null替代，此key中必须有3个参数 的长度，否则会报错例如“你好| | ”
	 * 
	 * @param activityName
	 *            活动名称
	 * @param stime
	 *            时间日期
	 * @param etime
	 *            时间日期
	 * @return
	 */
	public int findCountLogActivity(String key) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hsql.append(" SELECT COUNT(id)  FROM  LogActivitiesAward WHERE areaId = ? ");
		values.add(ServiceManager.getManager().getConfiguration().getString("areaid"));
		String[] params = key.split("\\|");
		boolean stimeNull = true; // 开始时间为空
		boolean etimeNull = true; // 介绍时间为空
		if (!params[0].equals(null) && !params[0].equals("") && !params[0].equals(" ")) {
			hsql.append(" AND activityName = ? ");
			values.add(params[0]);
		}
		if (!params[1].equals(null) && !params[1].equals("") && !params[1].equals(" ")) {
			stimeNull = false;
		}
		if (!params[2].equals(null) && !params[2].equals("") && !params[2].equals(" ")) {
			etimeNull = false;
		}
		try {
			if (!stimeNull && !etimeNull) {
				hsql.append(" AND startTime BETWEEN ? and ? ");
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[1]));
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[2]));
			} else if (!stimeNull) {
				hsql.append(" AND startTime = ? ");
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[1]));
			} else if (!etimeNull) {
				hsql.append(" AND endTime = ? ");
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[2]));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) this.count(hsql.toString(), values.toArray());
	}
}