package com.wyd.empire.world.dao;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;

/**
 * The DAO interface for the TabGuai entity.
 */
public interface IGuaiDao extends UniversalDao {
	/**
	 * 初始化怪物数据
	 */
	public void initData();

	/**
	 * GM工具查询出怪物列表
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findAllGuai(String key, int pageIndex, int pageSize);

	/**
	 * 根据怪物id返回怪物信息
	 * 
	 * @param guaiId
	 * @return
	 */
	public GuaiPlayer getGuaiById(int difficulty, int guaiId);
}