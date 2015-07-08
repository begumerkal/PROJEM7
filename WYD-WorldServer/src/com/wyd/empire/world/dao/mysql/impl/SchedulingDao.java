package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.dao.mysql.ISchedulingDao;
import com.wyd.empire.world.entity.mysql.LogActivitiesAward;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class SchedulingDao extends UniversalDaoHibernate implements ISchedulingDao {
	public SchedulingDao() {
		super();
	}

	/**
	 * 根据区域和是否已发放查询出活动发放日志记录
	 * 
	 * @param areaId
	 *            区域ID
	 * @param isSend
	 *            是否已发放
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LogActivitiesAward> findAllLogAward(String areaId, String isSend) {
		return this.getList("FROM " + LogActivitiesAward.class.getSimpleName() + " WHERE areaId=? AND isSend=?", new Object[]{areaId,
				isSend});
	}

	/**
	 * 获取今天已发送的登陆奖励
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getLoginAward(int playerId, Date date) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT activityName FROM " + LogActivitiesAward.class.getSimpleName() + " WHERE areaId=? ");
		values.add(WorldServer.config.getAreaId());
		hql.append(" AND playerId=? ");
		values.add(playerId);
		hql.append(" AND date(createTime)=date(?) ");
		values.add(date);
		return this.getList(hql.toString(), values.toArray());
	}
}