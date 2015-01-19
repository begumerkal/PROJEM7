package com.wyd.service.dao.impl;

import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.service.bean.WorldCupCode;
import com.wyd.service.bean.WorldCupPoints;
import com.wyd.service.dao.IWorldCupDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class WorldCupDao extends UniversalDaoHibernate implements IWorldCupDao {
	public WorldCupDao() {
		super();
	}
	public WorldCupPoints getByAccountId(String accountId){
		String hql = "from " + WorldCupPoints.class.getSimpleName() + " where accountId=? order by id desc";
         @SuppressWarnings("unchecked")
		List<WorldCupPoints> points = getList(hql, new Object[] { accountId});
         if(points==null || points.size()<1)return null;
         return points.get(0);
	}
	@Override
	public WorldCupCode getCode(int goals) {
		String hql = "from " + WorldCupCode.class.getSimpleName() + " where goals=? and sendTime is null ";
        @SuppressWarnings("unchecked")
		List<WorldCupCode> points = getList(hql, new Object[] { goals},1);
        if(points==null || points.size()<1)return null;
        return points.get(0);
	}
	

	

}