package com.wyd.empire.world.dao.mysql.impl;

import java.util.List;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.dao.mysql.ILimitedPriceDao;
import com.wyd.empire.world.entity.mysql.LimitedPrice;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class LimitedPriceDao extends UniversalDaoHibernate implements ILimitedPriceDao {
	public LimitedPriceDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public LimitedPrice getByCount(int itemdId, int count) {
		String hql = "FROM " + LimitedPrice.class.getSimpleName() + " v where v.count=? and v.itemId=?";
		Object[] values = new Object[]{count, itemdId};
		List<LimitedPrice> list = getList(hql.toString(), values);
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getMaxCount(int itemdId, int vipLevel) {
		String hql = "FROM " + LimitedPrice.class.getSimpleName() + " v where v.vipLevel<=? and v.itemId=? order by v.count desc";
		Object[] values = new Object[]{vipLevel, itemdId};
		List<LimitedPrice> list = getList(hql.toString(), values);
		if (null != list && list.size() != 0) {
			return list.get(0).getCount();
		}
		return 1;
	}

}