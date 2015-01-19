package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.ConsortiaSkill;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.dao.IConsortiaSkillDao;

public class ConsortiaSkillDao extends UniversalDaoHibernate implements IConsortiaSkillDao {
	public ConsortiaSkillDao() {
		super();
	}

	/**
	 * 获取公会技能分页列表
	 * 
	 * @param pageNum
	 * @param mark
	 * @return
	 */
	public PageList getConsortiaListSkill(int pageNum) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + ConsortiaSkill.class.getSimpleName() + " ORDER BY comLv asc ");
		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum - 1, Common.PAGESIZE);
	}
}
