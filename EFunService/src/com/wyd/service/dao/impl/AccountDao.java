package com.wyd.service.dao.impl;

import java.util.List;

import com.app.db.dao.impl.UniversalDaoHibernate;
import com.wyd.service.bean.Empireaccount;
import com.wyd.service.dao.IAccountDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class AccountDao extends UniversalDaoHibernate implements IAccountDao {
	public AccountDao() {
		super();
	}

	@Override
	public Empireaccount getEmpireaccount(String name) {
		String hql = "from " + Empireaccount.class.getSimpleName() + " where name=? order by id desc";
        @SuppressWarnings("unchecked")
		List<Empireaccount> empireaccount = getList(hql, new Object[] { name});
        if(empireaccount==null || empireaccount.size()<1)return null;
        return empireaccount.get(0);
	}

	

}