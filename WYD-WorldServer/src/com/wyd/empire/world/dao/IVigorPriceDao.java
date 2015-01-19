package com.wyd.empire.world.dao;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.bean.VigorPrice;

/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface IVigorPriceDao extends UniversalDao {
	public VigorPrice getByCount(int count);

	/**
	 * 获取最大购买次数
	 * 
	 * @param count
	 * @return
	 */
	public int getMaxCount(int vipLevel);

}