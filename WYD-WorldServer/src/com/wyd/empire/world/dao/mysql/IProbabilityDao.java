package com.wyd.empire.world.dao.mysql;

import java.util.Map;

import com.wyd.db.mysql.dao.UniversalDao;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IProbabilityDao extends UniversalDao {
	public Map<Integer, Integer> getpMap();

	/**
	 * 初始化数据
	 */
	public void initData();
}