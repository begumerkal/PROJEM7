package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.dao.IDailyActivityDao;
import com.wyd.empire.world.entity.mysql.DailyActivity;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class DailyActivityDao extends UniversalDaoHibernate implements IDailyActivityDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<DailyActivity> getAllDailyActivity() {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + DailyActivity.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND areaId = ? ");
		values.add(ServiceManager.getManager().getConfiguration().getString("areaid"));
		return getList(hql.toString(), values.toArray());
	}

	@Override
	public PageList getDailyActivityByPage(int pageIndex, int pageSize) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + DailyActivity.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND areaId = ? ");
		values.add(ServiceManager.getManager().getConfiguration().getString("areaid"));
		return getPageList(hql.toString(), "SELECT COUNT(id) " + hql, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 根据多个活动奖励ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	@Override
	public void deleteActivityByIds(String ids) {
		this.execute("DELETE " + DailyActivity.class.getSimpleName() + " WHERE id in (" + ids + ") ", new Object[]{});
	}
}
