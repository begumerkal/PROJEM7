package com.wyd.empire.world.dao.mysql;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;

public interface IConsortiaSkillDao extends UniversalDao {
	/**
	 * 获取公会技能分页列表
	 * 
	 * @param pageNum
	 * @param mark
	 * @return
	 */
	public PageList getConsortiaListSkill(int pageNum);
}
