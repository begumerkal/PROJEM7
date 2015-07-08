package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.dao.mysql.IShareDao;
import com.wyd.empire.world.entity.mysql.Share;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class ShareDao extends UniversalDaoHibernate implements IShareDao {
	public ShareDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Share> findBy(int shareType) {
		String hql = "from Share where shareType=?";
		List<Object> values = new ArrayList<Object>();
		values.add(shareType);
		return getList(hql, values.toArray());
	}

	@Override
	public Share getByTarget(String target) {
		String hql = "from Share where target=?";
		List<Object> values = new ArrayList<Object>();
		values.add(target);
		@SuppressWarnings("rawtypes")
		List results = getList(hql, values.toArray());
		if (results == null || results.size() < 1)
			return null;
		return (Share) results.get(0);
	}

}