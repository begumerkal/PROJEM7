package com.wyd.empire.world.server.service.base;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.ConsortiaSkill;

public interface IConsortiaSkillService extends UniversalManager {
	/**
	 * 添加一条公会技能数据
	 * 
	 * @param skill
	 */
	public void addConsortiaSkill(ConsortiaSkill skill);

	/**
	 * 更新公会技能
	 * 
	 * @param skill
	 */
	public void updateConsortiaSkill(ConsortiaSkill skill);

	/**
	 * 获取公会技能分页列表
	 * 
	 * @param pageNum
	 * @return
	 */
	public PageList getConsortiaListSkill(int pageNum);
}
