package com.wyd.empire.world.dao.impl;

import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.bean.VigorPrice;
import com.wyd.empire.world.dao.IVigorPriceDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class VigorPriceDao extends UniversalDaoHibernate implements IVigorPriceDao {
	public VigorPriceDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public VigorPrice getByCount(int count) {
		String hql = "FROM " + VigorPrice.class.getSimpleName() + " v where v.count=?";
		Object[] values = new Object[]{count};
		List<VigorPrice> list = getList(hql.toString(), values);
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getMaxCount(int vipLevel) {
		String hql = "FROM " + VigorPrice.class.getSimpleName() + " v where v.vipLevel<=? order by v.count desc";
		Object[] values = new Object[]{vipLevel};
		List<VigorPrice> list = getList(hql.toString(), values);
		if (null != list && list.size() != 0) {
			return list.get(0).getCount();
		}
		return 1;
	}

}