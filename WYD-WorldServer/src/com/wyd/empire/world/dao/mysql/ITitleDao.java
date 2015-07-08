package com.wyd.empire.world.dao.mysql;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.Title;

/**
 * The DAO interface for the TabChatRecord entity.
 */
public interface ITitleDao extends UniversalDao {
	/**
	 * 初始化称号基础数据
	 */
	public void initData();

	/**
	 * 获取所有称号数量
	 * 
	 * @return
	 */
	public int getAllTitleSize();

	/**
	 * 获取指定称号
	 * 
	 * @param titleId
	 * @return
	 */
	public Title getTitleById(int titleId);
}